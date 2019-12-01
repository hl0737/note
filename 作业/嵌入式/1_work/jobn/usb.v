`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date:    20:17:44 11/01/2016 
// Design Name: 
// Module Name:    usb 
// Project Name: 
// Target Devices: 
// Tool versions: 
// Description: 
//
// Dependencies: 
//
// Revision: 
// Revision 0.01 - File Created
// Additional Comments: 
//
//////////////////////////////////////////////////////////////////////////////////
`include "register.v"
module usb #(
  parameter DATA_WIDTH = 16
)(
   input			                     i_usb_ifclk,			 //   USB Clock inout
	input			                     i_usb_flaga,			 //	USB Flag  //CY68013 EP2 FIFO empty indication; 1:not empty; 0: empty
	input			                     i_usb_flagd,			 //	USB Flag  //CY68013 EP6 FIFO full indication; 1:not full; 0: full
	
   inout	   [DATA_WIDTH - 1 : 0]	   io_usb_data,			 //   USB Data bus 16 Bits
	
	output	[1:0]	                  o_usb_addr,				 //   USB Address bus 2 Bits
	output			                  o_usb_slrd,				 //   USB Read Enable
	output			                  o_usb_slwr,				 //	USB Write Enable
	output			                  o_usb_sloe,				 //	USB Output Enable
	output			                  o_usb_pkend 			 //	USB Packet end
);

localparam IDLE = 3'b000;
localparam SELECT_READ_FIFO = 3'b001;
localparam READ_FROM_USB = 3'b010;
localparam SELECT_WRITE_FIFO = 3'b011;
localparam WRITE_TO_USB = 3'b100;
localparam JUANJI = 3'b101;

reg [2 : 0]              state = 0;
reg [2 : 0]              state_nxt = 0;
reg [DATA_WIDTH - 1 : 0] buff [`MAXPKG - 1 : 0];
reg [`LOGMAXPKG - 1 : 0] counter = 0;
reg [`LOGMAXPKG - 1 : 0] number = 0;
reg                      usb_slrd = 1'b0;
reg                      usb_slwr = 1'b0;
reg                      usb_sloe = 1'b0;
reg                      usb_pkend = 1'b0;
reg [1 : 0]              usb_addr = 2'b00;
reg [DATA_WIDTH - 1 :  0]usb_data = 0;

assign o_usb_addr = usb_addr;
assign o_usb_slrd = usb_slrd;
assign o_usb_slwr = usb_slwr;
assign o_usb_sloe = usb_sloe;
assign o_usb_pkend= usb_pkend;
assign io_usb_data= (usb_sloe == 1'b1)? usb_data : 'bz;

always @(*) begin
   case (state)
	    IDLE: begin
			  // EP2 not empty
		     if (i_usb_flaga == 1'b1) begin
			  // Transform to SELECT_READ_FIFO
			      state_nxt = SELECT_READ_FIFO;
			  end
			  else begin
			      state_nxt = IDLE;
			  end
		 end
		 SELECT_READ_FIFO: begin
		    state_nxt = READ_FROM_USB;
		 end
		 READ_FROM_USB: begin
		    if((counter == `MAXPKG - 1) || (i_usb_flaga == 1'b0))begin
			     state_nxt = JUANJI;
          end
          else begin
			     state_nxt = READ_FROM_USB;
			 end
		 end
		 SELECT_WRITE_FIFO: begin
		     if(i_usb_flagd == 1'b1) begin
		         state_nxt = WRITE_TO_USB;
			  end
			  else begin
			      state_nxt = IDLE;
			  end
		 end
		 WRITE_TO_USB: begin
		    if(counter == number) begin
			     state_nxt = IDLE;
			 end
			 else begin
			     state_nxt = WRITE_TO_USB;
			 end
		 end
		 JUANJI: begin
		     state_nxt = SELECT_WRITE_FIFO; // 这里可能有问题，是不是应该计算完成再转成IDLE状态，要考虑一下
		 end
		 default: begin
		     state_nxt = IDLE;
		 end
	endcase
end

always @(posedge i_usb_ifclk) begin
    case (state)
	    IDLE: begin
		    usb_slrd <= 1'b1;
			 usb_slwr <= 1'b1;
			 usb_sloe <= 1'b1;
			 usb_pkend <= 1'b1;
			 usb_addr <= 2'b00;
			 counter <= 0;
			 number <= 0;
			 usb_data <= 0;
		 end
		 SELECT_READ_FIFO: begin
		    usb_slrd <= 1'b1;
			 usb_slwr <= 1'b1;
			 // sloe low
			 usb_sloe <= 1'b0;
			 usb_pkend <= 1'b1;
			 // select EP2
			 usb_addr <= 2'b00;
			 counter <= 0;
			 number <= 0;
			 usb_data <= 0;
		 end
		 READ_FROM_USB: begin
		    // If EP2 is not empty, to read
		    usb_slrd <= ~i_usb_flaga;
			 usb_slwr <= 1'b1;
			 // sloe low
			 usb_sloe <= 1'b0;
			 usb_pkend <= 1'b1;
			 // select EP2
			 usb_addr <= 2'b00;
			 // If EP2 is not empty
			 if(i_usb_flaga) begin
			     counter <= counter + 1'b1;
			 end
			 // To store into buffer
			 buff[counter] <= io_usb_data;
			 // To record the written number of words
			 number <= counter;
			 usb_data <= 0;
		 end
		 SELECT_WRITE_FIFO: begin
		    usb_slrd <= 1'b1;
			 usb_slwr <= 1'b1;
			 usb_sloe <= 1'b1;
			 if(i_usb_flagd == 1'b1) begin
			      usb_pkend <= 1'b1;
			 end
			 else begin
			      usb_pkend <= 1'b0;
			 end
			 // select EP6
			 usb_addr <= 2'b10;
			 counter <= 0;
			 usb_data <= 0;
		 end
		 WRITE_TO_USB: begin
		    usb_slrd <= 1'b1;
			 // If EP6 is not full, to write
			 usb_slwr <= ~i_usb_flagd;
			 usb_sloe <= 1'b1;
			 // If EP6 is full or written completely, submit data
			 if((i_usb_flagd == 1'b0) || (counter == number)) begin
			     usb_pkend <= 1'b0; 
			 end
			 else begin
			     usb_pkend <= 1'b1;
			 end
			 // select EP6
			 usb_addr <= 2'b10;
			 counter <= counter + 1'b1;
			 usb_data <= buff[counter];
		 end
		 JUANJI: begin
		 // 写死，一定是2x3矩阵，然后卷积核是2x2的，全是1
		 buff[8'd16] <= buff[4'd0] + buff[4'd1] + buff[4'd3] + buff[4'd4];
		 buff[8'd17] <= buff[4'd1] + buff[4'd2] + buff[4'd4] + buff[4'd5];
		 end
		 default: begin
		    usb_slrd <= 1'b1;
			 usb_slwr <= 1'b1;
			 usb_sloe <= 1'b1;
			 usb_pkend <= 1'b1;
			 usb_addr <= 2'b00;
			 counter <= 0;
			 number <= 0;
			 usb_data <= 0;
		 end
	 endcase
end

always @(posedge i_usb_ifclk) begin
   state <= state_nxt;
end
endmodule

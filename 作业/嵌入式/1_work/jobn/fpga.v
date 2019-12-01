`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date:    20:02:55 11/01/2016 
// Design Name: 
// Module Name:    fpga 
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

module fpga(
	//--------------	Clock Input ----------------------------------------------//
//	input			      CLOCK_48,				 //	24 MHz
	//--------------	LED ------------------------------------------------------//
	output	[3:0]	   LED,					    //	LED [3:0]
	//--------------  SDRAM Interface ------------------------------------------//
//	inout	   [15:0]	SDRAM_DQ,				 //	SDRAM Data bus 16 Bits
//	output	[12:0]	SDRAM_ADDR,				 //	SDRAM Address bus 13 Bits
//	output	[1:0]	   SDRAM_DQM,				 //	SDRAM Data Mask 
//	output			   SDRAM_WE_N,				 //	SDRAM Write Enable
//	output			   SDRAM_CAS_N,			 //	SDRAM Column Address Strobe
//	output			   SDRAM_RAS_N,			 //	SDRAM Row Address Strobe
//	output	[1:0]	   SDRAM_BA,				 //	SDRAM Bank Address 
//	output			   SDRAM_CLK,				 //	SDRAM Clock	
//	output			   SDRAM_CS_N,				 //	SDRAM Chip Select
//	output			   SDRAM_CKE,				 //	SDRAM Clock Enable
	//-------------	USB Interface ------------------------------------------//
	inout	   [15:0]	USB_DATA,				 //	USB Data bus 16 Bits
	output	[1:0]	   USB_ADDR,				 //	USB Address bus 2 Bits
	output			   USB_SLRD,				 //	USB Read Enable
	output			   USB_SLWR,				 //	USB Write Enable
	output			   USB_SLOE,				 //	USB Output Enable
	input			      USB_FLAGA,				 //	USB Flag
	input			      USB_FLAGB,				 //	USB Flag
	input			      USB_FLAGC,				 //	USB Flag
	input			      USB_FLAGD,				 //	USB Flag
	output			   USB_PKEND,				 //	USB Packet end
	//output			   USB_WU2,				    //	USB Wake Up USB2
	input			      USB_IFCLK				 //	USB Clock inout
//	input			      USB_CLK_OUT,			 //	USB Clock Output
//	input	   [1:0]	   USB_INT,				    //   USB Interrupt
	//------------    GPIO --------------------------------------------------//
//	inout	   [33:0]	GPIOA,					 //   GPIOA, Can Be Used as Differential Pairs
//	inout	   [33:0]	GPIOB					    //   GPIOB, Can Be Used as Differential Pairs
	);

//reg [24 :0] counter = 0;
//reg updown = 0;
//
//always @(posedge CLOCK_48) begin
//   if (counter == `DELAY) begin
//	    counter <= 0;
//		 updown <= ~updown;
//	end
//	else begin
//	   counter <= counter + 1'b1;
//	end
//end
assign LED[0] = USB_FLAGA;
assign LED[1] = USB_FLAGB;
assign LED[2] = USB_FLAGC;
assign LED[3] = USB_FLAGD;

usb #(
.DATA_WIDTH (16)
) u_usb (
   .i_usb_ifclk (USB_IFCLK),			 //   USB Clock inout
	.i_usb_flaga (USB_FLAGA),			 //	USB Flag  //CY68013 EP2 FIFO empty indication; 1:not empty; 0: empty
	.i_usb_flagd (USB_FLAGD),			 //	USB Flag  //CY68013 EP6 FIFO full indication; 1:not full; 0: full
	
   .io_usb_data (USB_DATA),			 //   USB Data bus 16 Bits
	
	.o_usb_addr  (USB_ADDR),			 //   USB Address bus 2 Bits
	.o_usb_slrd  (USB_SLRD),			 //   USB Read Enable
	.o_usb_slwr  (USB_SLWR),			 //	USB Write Enable
	.o_usb_sloe  (USB_SLOE),			 //	USB Output Enable
	.o_usb_pkend (USB_PKEND)			 //	USB Packet end
);

endmodule

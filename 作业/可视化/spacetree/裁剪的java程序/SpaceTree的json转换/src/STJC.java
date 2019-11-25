import bean.City;
import bean.Origin;
import bean.To;
import com.alibaba.fastjson.JSON;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class STJC {
    public static void main(String[] args) {
        try {
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(STJC.class.getResourceAsStream("data.json")));
            String t = null;
            while ((t = reader.readLine()) != null) {
                builder.append(t);
            }
            reader.close();
            List<Origin> origins = JSON.parseArray(builder.toString(), Origin.class);
            To to = convert(origins);
            scaleData(to);
            String text = JSON.toJSONString(to);
            System.out.println(text);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void scaleData(To to) {
        to.children = to.children.subList(2, 4);
    }

    private static To convert(List<Origin> origins) {
        To to = new To();
        to.name="中国";
        to.parent="null";
        to.children=convert1(origins);
        return to;
    }

    private static List<To> convert1(List<Origin> origins) {
        List<To> r = new ArrayList<>();
        for (Origin origin : origins) {
            To to = new To();
            to.name = origin.name;
            to.parent = "中国";
            to.children = convert2(origin);
            r.add(to);
        }
        return r;
    }

    private static List<To> convert2(Origin origin) {
        List<To> r = new ArrayList<>();
        for (City city : origin.city) {
            To to = new To();
            to.name = city.name;
            to.parent = origin.name;
            to.children = convert3(city);
            r.add(to);
        }
        return r;
    }

    private static List<To> convert3(City city) {
        List<To> r = new ArrayList<>();
        for (String district : city.area) {
            To to = new To();
            to.name = district;
            to.parent = city.name;
            to.children = null;
            r.add(to);
        }
        return r;
    }
}

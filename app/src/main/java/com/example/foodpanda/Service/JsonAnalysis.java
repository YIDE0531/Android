package com.example.foodpanda.Service;

import com.example.foodpanda.Model.AllModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class JsonAnalysis {
    public String msg, result, name, photo, friendname, num, otherUserId, friendtoken, image;
    public ArrayList<Data> friendList = new ArrayList<Data>();
    public ArrayList<Data> invitationList = new ArrayList<Data>();
    AllModel shop;
    static Data datalist;

    public AllModel parser (String phpname, String data) throws JSONException {
        JSONObject jsonObjectre = new JSONObject(data);

        switch (phpname){
            case "getData":       //註冊使用者
                //JSONArray array = jsonObjectre.getJSONArray("result");
                result = jsonObjectre.getString("title");
                image = jsonObjectre.getString("image");
                shop = new AllModel(image, result);
                break;
            case "login":       //登入
                //JSONArray array = jsonObjectre.getJSONArray("result");
                result = jsonObjectre.getString("result");
                msg = jsonObjectre.getString("message");
                num = jsonObjectre.getString("num");
                for(int i=0;i<Integer.parseInt(num);i++) {
                    datalist = new Data(jsonObjectre.getString("friendname"+i),jsonObjectre.getString("friendaccount"+i));
                    friendList.add(datalist);
                }
                break;
            case "fblogin":       //fb註冊使用者
                //JSONArray array = jsonObjectre.getJSONArray("result");
                result = jsonObjectre.getString("result");
                msg = jsonObjectre.getString("message");
                break;
            case "qrcodefind":       //qrcode找使用者
                //JSONArray array = jsonObjectre.getJSONArray("result");
                result = jsonObjectre.getString("result");
                msg = jsonObjectre.getString("message");
                name = jsonObjectre.getString("name");
                photo = jsonObjectre.getString("photo");
                break;
            case "insertfriend":       //加入好友
                //JSONArray array = jsonObjectre.getJSONArray("result");
                result = jsonObjectre.getString("result");
                msg = jsonObjectre.getString("message");
                break;
            case "updateuserinformation":       //修改使用者資料
                //JSONArray array = jsonObjectre.getJSONArray("result");
                result = jsonObjectre.getString("result");
                msg = jsonObjectre.getString("message");
                break;
            case "setonline":       //修改使用者資料
                //JSONArray array = jsonObjectre.getJSONArray("result");
                result = jsonObjectre.getString("result");
                msg = jsonObjectre.getString("message");
                break;
            case "getfriendonlineID":       //修改使用者資料
                //JSONArray array = jsonObjectre.getJSONArray("result");
                result = jsonObjectre.getString("result");
                msg = jsonObjectre.getString("message");
                otherUserId = jsonObjectre.getString("onlineID");
                friendtoken = jsonObjectre.getString("friendtoken");
                break;
            case "invitation":       //好友邀請
                //JSONArray array = jsonObjectre.getJSONArray("result");
                result = jsonObjectre.getString("result");
                msg = jsonObjectre.getString("message");
                num = jsonObjectre.getString("num");

                for(int i=0;i<Integer.parseInt(num);i++) {
                    datalist = new Data(jsonObjectre.getString("friendname"+i),jsonObjectre.getString("friendaccount"+i));
                    invitationList.add(datalist);
                }
                break;
        }
        return shop;
    }

    public class Data {
        public String friendname;
        public String friendaccount;
        Data(String friendname, String friendaccount) {
            this.friendname = friendname;
            this.friendaccount = friendaccount;
        }
    }

    public String buildJsonString(HashMap<String, String> map) {     //字串map 轉成 json格式
        String jsonText = "";
        JSONObject jsonObject = new JSONObject();
        try {
            Set<String> set = map.keySet();
            Iterator<String> iterator = set.iterator();
            String key = "";
            while(iterator.hasNext()){
                key = iterator.next();
                jsonObject.put(key, map.get(key)) ;
            }
            jsonText =  jsonObject.toString();
        } catch (JSONException e) {
        }
        return jsonText;
    }


}

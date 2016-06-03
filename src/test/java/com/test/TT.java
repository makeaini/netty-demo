package com.test;

import com.alibaba.fastjson.JSON;
import com.pppcar.netty.message.Message;

public class TT {
	public static void main(String[] args) {
	      Message msg=new Message("dfdf",2);
          String s=JSON.toJSONString(msg);
          System.out.println(s);
          //{"id":"dfdf","messageType":2}
	}

}

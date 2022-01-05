package com.khjxiaogu.fss;

import java.io.File;

import com.khjxiaogu.webserver.Utils;
import com.khjxiaogu.webserver.builder.BasicWebServerBuilder;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		BasicWebServerBuilder.build().createWrapperRoot(new DataStore(new File("."))).complete().compile().serverHttp("0.0.0.0",Integer.parseInt(new String(Utils.readAll(new File("port.txt"))))).await();
	}

}

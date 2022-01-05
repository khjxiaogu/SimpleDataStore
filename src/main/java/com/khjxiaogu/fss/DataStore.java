package com.khjxiaogu.fss;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.khjxiaogu.webserver.Utils;
import com.khjxiaogu.webserver.annotations.Adapter;
import com.khjxiaogu.webserver.annotations.HttpMethod;
import com.khjxiaogu.webserver.annotations.HttpPath;
import com.khjxiaogu.webserver.annotations.Query;
import com.khjxiaogu.webserver.web.AbstractServiceClass;
import com.khjxiaogu.webserver.web.FilePageService;
import com.khjxiaogu.webserver.web.lowlayer.Request;
import com.khjxiaogu.webserver.web.lowlayer.Response;
import com.khjxiaogu.webserver.wrappers.ResultDTO;

public class DataStore extends AbstractServiceClass {
	FilePageService fs;
	String token;
	File path;
	public DataStore(File path) {
		path.mkdirs();
		this.path=new File(path, "/page");
		this.path.mkdirs();
		fs= new FilePageService(this.path);
		token=new String(Utils.readAll(new File(path,"token.txt")));
	}
	@HttpPath("/write")
	@HttpMethod("GET")
	@Adapter
	public ResultDTO UploadProfile(@Query("token")String token, @Query("name")String name, @Query("value")String value) {
		if (!this.token.equals(token))
			return new ResultDTO(404);
		File f=new File(path,name);
		f.getParentFile().mkdirs();
		try(FileOutputStream fos=new FileOutputStream(f)){
			fos.write(value.getBytes(StandardCharsets.UTF_8));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResultDTO(200,value);
	}

	@HttpPath("/data")
	@HttpMethod("GET")
	public void getFile(Request req,Response res) {
		fs.call(req, res);
	}
	@Override
	public String getName() {
		return "ShortDataStore";
	}

}

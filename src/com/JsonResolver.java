package com;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonResolver {
    private static final Logger LOG = LoggerFactory.getLogger(JsonResolver.class);
    private static final  String separator = "\001";
    private static final String TYPE_PUBLISH = "rtmp_publish";
    private static final String TYPE_RELAY = "request_relay";
    private static final String[] ARY_RELAY = new String[]{
    		"peer_id",
    		"log_len",
    		"ip",
    		"app_id",
    		"public_ip_city",
    		"session",
    		"public_ip_country",
    		"port",
    		"msg_cmd",
    		"returnvalue",
    		"public_ip_longitude",
    		"public_ip_latitude",
    		"timestamp",
    		"public_ip_continent",
    		"public_ip_isp",
    		"log_type",
    		"public_ip_en",
    		"create_at",
    		"public_ip_area",
    		"public_ip_cc",
    		"client_type",
    		"relay_port",
    		"relay_ip",
    		"public_ip_code",
    		"public_ip_prov",
    		"broker"
    };
    		
    
    private static final String[] ARY_PUBLISH = new String[]{
    		"peer_id",
    		"app_id",
    		"beat",
    		"bitrate",
    		"broker",
    		"client_type",
    		"create_at",
    		"framerate",
    		"geoip",
    		"host",
    		"input_type",
    		"ip",
    		"log_len",
    		"log_type",
    		"msg_cmd",
    		"port",
    		"public_ip_area",
    		"public_ip_cc",
    		"public_ip_city",
    		"public_ip_code",
    		"public_ip_continent",
    		"public_ip_country",
    		"public_ip_en",
    		"public_ip_isp",
    		"public_ip_latitude",
    		"public_ip_longitude",
    		"public_ip_prov",
    		"send_rate",
    		"send_time",
    		"session",
    		"tags",
    		"time_toal",
    		"timestamp",
    		"viewers",
    		"wait_time"
    	};
    
    
	public static void main(String[] args) {
//		String inpath = SystemProperties.getInstance().getValues("inpath", "G:/yehao/00.test_data/data.json");
//		String outpath = SystemProperties.getInstance().getValues("outpath", "G:/yehao/00.test_data/data1.json");
		if(args.length != 3){
			System.err.println("Usage: JsonResolver <in> <out> <type>");
			System.exit(2);
		}
		String inpath =  args[0];
		String outpath =  args[1];
		String type = args[2];
		LOG.info("inpath:"+inpath);
		LOG.info("outpath:"+outpath);
		LOG.info("type:"+type);
		
		Map<String, Object> map = new HashMap<String, Object>();  
        ObjectMapper mapper = new ObjectMapper();  
        String str = "";
        try{  
            map = mapper.readValue(new File(inpath), new TypeReference<HashMap<String,Object>>(){});  
            
			Map<String, Object>  hits = (Map<String, Object>) map.get("hits");
            String[] ary;
            if(TYPE_PUBLISH.equals(type)){
        		ary = ARY_PUBLISH;
        	}else if(TYPE_RELAY.equals(type)){
        		ary = ARY_RELAY;
        	}else{
        		LOG.error("The Exception occured. type is wrong");  
        		return;
        	}
            List<Map<String, Object>> list = (List<Map<String, Object>>) hits.get("hits");
            for(Map<String, Object> data : list){
            	Map<String, Object> dataMap = (Map<String, Object>)data.get("_source");
            	
            	for(String col : ary){
            		str += dataMap.get(col) + separator;
            	}
            	str += "\n";
            }
             
            if(!"".equals(str)){
            	File file = new File(outpath);

        	   if (!file.exists()) {
        	    file.createNewFile();
        	   }

        	   FileWriter fw = new FileWriter(file.getAbsoluteFile());
        	   BufferedWriter bw = new BufferedWriter(fw);
        	   bw.write(str);
        	   bw.close();

            }
        }catch(Exception e){
    		LOG.error("The Exception occured.", e);  
        }  	 
	}
}

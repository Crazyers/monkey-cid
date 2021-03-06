package org.monkey.cid.core.util;

import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LinuxCommandUtil {
	
	private static Logger logger = LoggerFactory.getLogger(LinuxCommandUtil.class);
	
	public static String exec(List<String> commands, BlockingQueue<String> queue) throws Exception {
		String cmds = "";
        for (String cmd : commands) {
            cmds += cmd + ";";
        }
        logger.info(cmds);
        String[] cmdA = {"/bin/sh", "-c", cmds};
        Process process = Runtime.getRuntime().exec(cmdA);
        LineNumberReader br = new LineNumberReader(new InputStreamReader(process.getInputStream()));
        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = br.readLine()) != null) {
        	logger.info(line);
        	if (queue != null) {
        		queue.add(line);
        	}
            sb.append(line).append("\n");
        }
        br.close();
        process.destroy();
        return sb.toString();
    }
 
    public static String exec(String cmd, BlockingQueue<String> queue) throws Exception {
    	return exec(Arrays.asList(cmd), queue);
    }

}

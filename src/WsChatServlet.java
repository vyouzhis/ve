import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import org.apache.catalina.websocket.WsOutbound;
import org.eclipse.jdt.internal.compiler.ast.ThisReference;
import org.ppl.io.TimeClass;

import com.alibaba.fastjson.JSON;

@SuppressWarnings("deprecation")
public class WsChatServlet extends WebSocketServlet{
    private static final long serialVersionUID = 1L;
    private static ArrayList<MyMessageInbound> mmiList = new ArrayList<MyMessageInbound>();
    
    private class MyMessageInbound extends MessageInbound{
        WsOutbound myoutbound;
        long key;
        @Override
        public void onOpen(WsOutbound outbound){
        	System.out.println("WsChatServlet 2");
            try {
                System.out.println("Open Client.");
             
                this.myoutbound = outbound;
                mmiList.add(this);
                TimeClass tcClass = TimeClass.getInstance();
                key = tcClass.time();
                outbound.writeTextMessage(WelCome());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onClose(int status){
            System.out.println("Close Client.");
            mmiList.remove(this);
        }

        @Override
        public void onTextMessage(CharBuffer cb) throws IOException{
            System.out.println("Accept Message : "+ cb);
            TimeClass tcClass = TimeClass.getInstance();
        	//List<Map<String, Object>> tMsg = new ArrayList<>();
        	
            for(MyMessageInbound mmib: mmiList){
                //CharBuffer buffer = CharBuffer.wrap(cb);
                Map<String, Object> welcome = new HashMap<String, Object>();
                int me = 0; // 0 other, 1 me
                System.out.println("list key:"+mmib.key +"  my key:"+key);
                if(mmib.key == key) me=1;
                
                welcome.put("user_me", me);
            	welcome.put("user_logo", "avatar5.png");
            	welcome.put("user_name", "discovery");
            	welcome.put("user_time", tcClass.time());
            	welcome.put("user_msg", cb.toString());
            	String msgJson = JSON.toJSONString(welcome);
            	
                mmib.myoutbound.writeTextMessage(CharBuffer.wrap(msgJson));
                mmib.myoutbound.flush();
            }
        }

        @Override
        public void onBinaryMessage(ByteBuffer bb) throws IOException{
        	System.out.println("WsChatServlet 3");
        }
        
        private CharBuffer WelCome() {
        	TimeClass tcClass = TimeClass.getInstance();
        	//List<Map<String, Object>> welcome = new ArrayList<>();
        	Map<String, Object> welcome = new HashMap<String, Object>();
        	
        	welcome.put("user_me", 0);
        	welcome.put("user_logo", "avatar5.png");
        	welcome.put("user_name", "discovery");
        	welcome.put("user_time", tcClass.time());
        	welcome.put("user_msg", "Hello!  WelCome to chat!");
        	//welcome.add(map);
        	String msgJson = JSON.toJSONString(welcome);
        	
        	return CharBuffer.wrap(msgJson);
		}
               
    }

	@Override
	protected StreamInbound createWebSocketInbound(String arg0,
			HttpServletRequest arg1) {
		// TODO Auto-generated method stub
		System.out.println("WsChatServlet 1");
		return new MyMessageInbound();
	}


}

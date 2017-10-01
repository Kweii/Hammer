import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by gui on 2017/9/24.
 */
public class TestRpc {
    Logger logger = LogManager.getLogger(TestRpc.class);

    @Test
    public void testCodec() throws Exception {
//        MsgHeader header = new MsgHeader(1024, MsgEnum.SERVICE_MSG_REQ, (byte )1, new HashMap<String, Object>());
//        MsgBody body = new ServiceInvocation("test-chain-33", "com.guiliehua.service.TestService", "sayHello", new Class[]{String.class}, new Object[]{"someOne"}, "1.0");
//
//        HammerMsg msg = new HammerMsg(header, body);
//
//        List<Object> buffers = new LinkedList<Object>();
//        FastJsonEncoder encoder = new FastJsonEncoder();
//        encoder.testEncode(null, msg, buffers);
//
//
//        FastJsonDecoder decoder = new FastJsonDecoder(1024*1024, FastJsonEncoder.HAMMER_MSG_LENGTH_POS_IDX, 4);
//
//        decoder.decode((ByteBuf)buffers.get(0));
        List<String> list = new LinkedList<String>();
        logger.info(list.getClass().equals(LinkedList.class));

    }
}

import com.hammer.rpc.endpoint.codec.HammerDecoder;
import com.hammer.rpc.endpoint.codec.HammerEncoder;
import com.hammer.rpc.msg.HammerMsg;
import com.hammer.rpc.msg.body.MsgBody;
import com.hammer.rpc.msg.body.ServiceInvocation;
import com.hammer.rpc.msg.common.MsgEnum;
import com.hammer.rpc.msg.header.MsgHeader;
import io.netty.buffer.ByteBuf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by gui on 2017/9/24.
 */
public class TestRpc {
    Logger logger = LogManager.getLogger(TestRpc.class);

    @Test
    public void testCodec() throws Exception {
        MsgHeader header = new MsgHeader(1024, MsgEnum.SERVICE_MSG_REQ, (byte )1, new HashMap<String, Object>());
        MsgBody body = new ServiceInvocation("test-chain-33", "com.guiliehua.service.TestService", "sayHello", new Class[]{String.class}, new Object[]{"someOne"}, "1.0");

        HammerMsg msg = new HammerMsg(header, body);

        List<Object> buffers = new LinkedList<Object>();
        HammerEncoder encoder = new HammerEncoder();
        encoder.testEncode(null, msg, buffers);


        HammerDecoder decoder = new HammerDecoder(1024*1024, HammerEncoder.HAMMER_MSG_LENGTH_POS_IDX, 4);

        decoder.decode((ByteBuf)buffers.get(0));

    }
}

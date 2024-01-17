package go.communications;

import java.io.Serializable;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class Request implements Serializable {
    public final Serializable content;
    public final Checksum crc;

    public Request(Serializable content) {
        this.content = content;
        this.crc = new CRC32();
        crc.update(content.toString().getBytes());
    }

    public boolean checkCRC() {
        CRC32 crc = new CRC32();
        crc.update(content.toString().getBytes());
        return crc.getValue() == this.crc.getValue();
    }
}

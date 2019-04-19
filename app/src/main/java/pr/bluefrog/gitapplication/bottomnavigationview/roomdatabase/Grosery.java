package pr.bluefrog.gitapplication.bottomnavigationview.roomdatabase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Grosery implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name ="u_id")
    private int uid;

    @ColumnInfo(name = "groseryname")
    private String groseryname;

    @ColumnInfo(name = "groseryItems")
    private String groseryItems;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getGroseryname() {
        return groseryname;
    }

    public void setGroseryname(String groseryname) {
        this.groseryname = groseryname;
    }

    public String getGroseryItems() {
        return groseryItems;
    }

    public void setGroseryItems(String groseryItems) {
        this.groseryItems = groseryItems;
    }
}

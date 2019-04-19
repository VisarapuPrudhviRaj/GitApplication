package pr.bluefrog.gitapplication.bottomnavigationview.roomdatabase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Products implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name ="u_id")
    private int uid;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "designation")
    private String Desgination;


    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesgination() {
        return Desgination;
    }

    public void setDesgination(String desgination) {
        Desgination = desgination;
    }
}

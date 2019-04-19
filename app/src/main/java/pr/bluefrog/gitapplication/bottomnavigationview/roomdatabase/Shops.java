package pr.bluefrog.gitapplication.bottomnavigationview.roomdatabase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Shops implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name ="u_id")
    private int uid;

    @ColumnInfo(name = "shopname")
    private String shopname;

    @ColumnInfo(name = "shopItems")
    private String shopItems;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getShopItems() {
        return shopItems;
    }

    public void setShopItems(String shopItems) {
        this.shopItems = shopItems;
    }
}

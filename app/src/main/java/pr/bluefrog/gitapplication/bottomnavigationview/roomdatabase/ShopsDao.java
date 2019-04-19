package pr.bluefrog.gitapplication.bottomnavigationview.roomdatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ShopsDao {
    @Query("SELECT * FROM shops")
    List<Shops> getAll();

    @Query("SELECT * FROM shops WHERE shopname LIKE :shopname LIMIT 1")
    Shops findByName(String shopname);

    @Query("SELECT * FROM shops WHERE u_id =:Id")
    Shops findById(int Id);

    @Insert
    void insertAll(List<Shops> shops);

    @Update
    void update(Shops shops);

    @Query("UPDATE shops SET shopname = :shopname, shopItems = :shopItems WHERE u_id =:Id")
    void update(String shopname, String shopItems, int Id);

    @Delete
    void delete(Shops shops);

    @Query("DELETE FROM shops WHERE u_id = :Id")
    void delete(int Id);





}

package pr.bluefrog.gitapplication.bottomnavigationview.roomdatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface GroseryDao {
    @Query("SELECT * FROM grosery")
    List<Grosery> getAll();

    @Query("SELECT * FROM grosery WHERE groseryname LIKE :groseryname LIMIT 1")
    Grosery findByName(String groseryname);

    @Query("SELECT * FROM grosery WHERE u_id =:Id")
    Grosery findById(int Id);

    @Insert
    void insertAll(List<Grosery> grosery);

    @Update
    void update(Grosery grosery);

    @Query("UPDATE grosery SET groseryname = :groseryname, groseryItems = :groseryItems WHERE u_id =:Id")
    void update(String groseryname, String groseryItems, int Id);

    @Delete
    void delete(Grosery grosery);

    @Query("DELETE FROM grosery WHERE u_id = :Id")
    void delete(int Id);





}

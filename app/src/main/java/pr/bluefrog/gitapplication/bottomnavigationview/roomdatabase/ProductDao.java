package pr.bluefrog.gitapplication.bottomnavigationview.roomdatabase;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.arch.persistence.room.migration.Migration;

import java.util.List;

@Dao
public interface ProductDao {
    @Query("SELECT * FROM products")
    List<Products> getAll();

    @Query("SELECT * FROM products WHERE name LIKE :name LIMIT 1")
    Products findByName(String name);

    @Query("SELECT * FROM products WHERE u_id =:Id")
    Products findById(int Id);

    @Insert
    void insertAll(List<Products> products);

    @Update
    void update(Products product);

    @Query("UPDATE products SET name = :name, designation = :designation WHERE u_id =:Id")
    void update(String name, String designation, int Id);

    @Delete
    void delete(Products product);

    @Query("DELETE FROM products WHERE u_id = :Id")
    void delete(int Id);





}

package le.minh.hau.ck01;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface FoodDAO {

    @Insert(onConflict = REPLACE)
    void insert(Food food);

    @Delete
    void delete(Food food);

    @Delete
    void reset(List<Food> foodList);

    @Query("UPDATE food SET  ten= :sten,hinh= :shinh,gia= :sgia WHERE id= :sid")
    void update (int sid,String sten,int shinh,double sgia);

    @Query("SELECT * FROM food")
    List<Food> getall();

    @Query("SELECT * FROM food WHERE ten LIKE '%' || :sten || '%'")
    List<Food> searchbyName(String sten);


}

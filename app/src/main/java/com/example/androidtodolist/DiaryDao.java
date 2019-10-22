    package com.example.androidtodolist;

    import androidx.room.Dao;
    import androidx.room.Delete;
    import androidx.room.Insert;
    import androidx.room.Query;
    import androidx.room.Update;

    import java.util.List;

    @Dao
    public interface DiaryDao {
        @Query("SELECT * FROM Diary")
        List<Diary> getAll();

        @Insert
        void insert(Diary diary);

        @Delete
        void delete(Diary diary);

        @Update
        void updateOne(Diary diary);

    }

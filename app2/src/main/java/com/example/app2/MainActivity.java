package com.example.app2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
//    RecyclerView listView;
    ListView listView;
    List<Fruit> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=(ListView) findViewById(R.id.lv);
        data=new ArrayList<>();
        data.add(new Fruit("苹果","https://upload.jianshu.io/users/upload_avatars/12642878/cb3f18b9-6e6c-4aea-a618-5499940abba6.png?imageMogr2/auto-orient/strip|imageView2/1/w/240/h/240"));
        data.add(new Fruit("橘子","https://upload.jianshu.io/users/upload_avatars/12642878/cb3f18b9-6e6c-4aea-a618-5499940abba6.png?imageMogr2/auto-orient/strip|imageView2/1/w/240/h/240"));
        data.add(new Fruit("梨","https://upload.jianshu.io/users/upload_avatars/12642878/cb3f18b9-6e6c-4aea-a618-5499940abba6.png?imageMogr2/auto-orient/strip|imageView2/1/w/240/h/240"));
        data.add(new Fruit("香蕉","https://upload.jianshu.io/users/upload_avatars/12642878/cb3f18b9-6e6c-4aea-a618-5499940abba6.png?imageMogr2/auto-orient/strip|imageView2/1/w/240/h/240"));
        data.add(new Fruit("菠萝","https://upload.jianshu.io/users/upload_avatars/12642878/cb3f18b9-6e6c-4aea-a618-5499940abba6.png?imageMogr2/auto-orient/strip|imageView2/1/w/240/h/240"));
        data.add(new Fruit("桃子","https://upload.jianshu.io/users/upload_avatars/12642878/cb3f18b9-6e6c-4aea-a618-5499940abba6.png?imageMogr2/auto-orient/strip|imageView2/1/w/240/h/240"));
        data.add(new Fruit("橙子","https://upload.jianshu.io/users/upload_avatars/12642878/cb3f18b9-6e6c-4aea-a618-5499940abba6.png?imageMogr2/auto-orient/strip|imageView2/1/w/240/h/240"));

//        StaggeredGridLayoutManager recyclerViewLayoutManager =
//                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//        listView.setLayoutManager(recyclerViewLayoutManager);
        MyListAdapter adapter=new MyListAdapter(this,R.layout.fruit_item,data);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),data.get(position).getName(),Toast.LENGTH_SHORT).show();
            }
        });

    }
}

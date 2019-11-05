package com.example.kwons.music_in_you;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Search_MusicActivity extends AppCompatActivity {


    static Search_MusicActivity search_musicActivity;
    private EditText search_text; // 검색어를 입력할 텍스트 창
    private ArrayList<MusicDTO> searach_list;
    private ListView listView; // 리스트들을 보여줄 뷰
    private SongAdapter adapter; // 리스트 뷰에 연결할 어뎁터
    private ArrayList<MusicDTO> list; // 데이터를 넣은 리스트
    private TextView text; // 다이얼로그 창에 보여질 메시지
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_music);
        listView = (ListView) findViewById(R.id.search_listview);
        search_text = (EditText) findViewById(R.id.search_text);

        list = new ArrayList<MusicDTO>();

        // 원본 데이터
        list = MainActivity.mainActivity.getMusicList();

        searach_list = new ArrayList<MusicDTO>();
        searach_list.addAll(list); // 원본을 복사함
        adapter = new SongAdapter(this, list);
        listView.setAdapter(adapter);

        search_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 검색어 입력 전에 호출
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 검색어 입력 완료시 호출
            }

            @Override
            public void afterTextChanged(Editable s) {
                // 검색어 입력시 호출

                String text = search_text.getText().toString(); // 입력한 검색어를 스트링 변수에 저장
                listView.setVisibility(View.VISIBLE);
                search(text);

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("아이디 ",""+id+", 포지션: "+position + "list.get(position).getId(): "+ list.get(position).getId());
                //Intent intent = new Intent(Search_MusicActivity.this, DialogActivity.class);
                Intent intent = new Intent(Search_MusicActivity.this, MusicPlayActivity.class);
                intent.putExtra("playlist_position", position);
                intent.putExtra("playlist", list);
                startActivity(intent);


            }
        });
    }


    // 검색을 수행하는 메소드

    public void search(String charText) {
        list.clear();

        if (charText == null) {
            list.addAll(searach_list);
        }
        else {
            for (int i = 0; i < searach_list.size(); i++) {
                System.out.println("리스트 사이즈:"+list.size());
                if ((searach_list.get(i).getArtist().contains(charText)) || (searach_list.get(i).getTitle().contains(charText))) {

                    list.add(searach_list.get(i));

                }
            }
        }
        adapter.notifyDataSetChanged();

    }

/*
    @Override
    public void onBackPressed(){
        finish();
    }
*/
}


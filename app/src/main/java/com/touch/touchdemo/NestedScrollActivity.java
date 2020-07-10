package com.touch.touchdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class NestedScrollActivity extends AppCompatActivity {

    private SwipeRefreshLayout mSrl;
    private static Handler mHandler = new Handler();
    private ChildAdapter mAdapter;
    private View mTopBar;
    private LinearLayout mLlBar;
    private MyNestedScrollerView mMnsv;
//    private ImageView mIvGoTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested_scroll);
        mSrl = findViewById(R.id.srl);
        mTopBar = findViewById(R.id.ll_top_bar);
        mTopBar.setVisibility(View.GONE);
        mSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMoreData();
            }
        });
        mLlBar = findViewById(R.id.ll_bar);
        mLlBar.setVisibility(View.VISIBLE);
        mMnsv = findViewById(R.id.mnsv);
        //mIvGoTop = findViewById(R.id.iv_go_top);
        RecyclerView rlv = findViewById(R.id.rlv);
        rlv.setLayoutManager(new LinearLayoutManager(this));
        rlv.setAdapter(new MyAdapter());

        TabLayout tl = findViewById(R.id.tl);
        ViewPager vp = findViewById(R.id.vp);
        List<String> titles = new ArrayList<>();
        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            titles.add("第" + (i + 1) + "页");
            fragments.add(new HomeFragment());
        }
        FAdapter fAdapter = new FAdapter(getSupportFragmentManager(),titles,fragments);
        vp.setAdapter(fAdapter);
        tl.setupWithViewPager(vp);

        mMnsv.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                Log.i("weilgu","mnsv.setOnScrollChangeListener = " + totalScrollY);
                Log.i("weilgu","mnsv.scrollY = " + scrollY);
                if (scrollY <= 0){
                    mTopBar.getBackground().mutate().setAlpha(0);
                    mLlBar.setVisibility(View.VISIBLE);
                    mTopBar.setVisibility(View.GONE);
                }else if (scrollY < dp2px(120)){
                    int alpha = scrollY * 255 / dp2px(120);
                    mLlBar.setAlpha(alpha);
                }else{
                    mTopBar.setVisibility(View.VISIBLE);
                    mLlBar.setVisibility(View.GONE);
                    mTopBar.getBackground().mutate().setAlpha(255);
                }
            }
        });
        /*mIvGoTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
    }

    private void getMoreData() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mMnsv.getChildRV() != null && mMnsv.getChildRV().getAdapter() != null){
                    HomeFragment.ChildAdapter adapter = (HomeFragment.ChildAdapter) mMnsv.getChildRV().getAdapter();
                    adapter.getDatas().clear();
                    for (int i = 0; i < 30; i++) {
                        adapter.getDatas().add(new ChildMultiType(1));
                    }
                    adapter.notifyDataSetChanged();
                }
                mSrl.setRefreshing(false);
            }
        },1000);
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder>{

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_rv, parent, false);
            return new MyHolder(view,viewType);
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 3;
        }

        class MyHolder extends RecyclerView.ViewHolder{

            public MyHolder(@NonNull View itemView, int itemType) {
                super(itemView);
                View top = itemView.findViewById(R.id.v_top);
            }
        }
    }

    class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.ChildeHolder>{

        List<ChildMultiType> mDatas;

        public ChildAdapter(){
            mDatas = new ArrayList<>();
            for (int i = 0; i < 30; i++) {
                mDatas.add(new ChildMultiType(1));
            }
        }

        @Override
        public int getItemViewType(int position) {
            return mDatas.get(position).getType();
        }

        @NonNull
        @Override
        public ChildeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_child_rv, parent, false);
            return new ChildeHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ChildeHolder holder, int position) {
            int itemViewType = getItemViewType(position);
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        class ChildeHolder extends RecyclerView.ViewHolder{

            public ChildeHolder(@NonNull View itemView) {
                super(itemView);
            }
        }

        public List<ChildMultiType> getDatas() {
            return mDatas;
        }
    }

    public static int dp2px(float dipValue) {
        final float scale = Resources.getSystem().getDisplayMetrics() != null ? Resources.getSystem().getDisplayMetrics().density : 1;
        return (int) (dipValue * scale + 0.5f);
    }

    public MyNestedScrollerView getMnsv() {
        return mMnsv;
    }
}

package com.touch.touchdemo;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author weilgu
 * @time 2020/7/9
 * @des
 */
public class HomeFragment extends Fragment implements ILoadMoreListener{

    private RecyclerView mChild;
    private static Handler mHandlers = new Handler();
    private ImageView mGoTop;
    private MyNestedScrollerView mMnsv;
    private boolean isScrollTop = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        mChild = view.findViewById(R.id.rv_child);
        mGoTop = view.findViewById(R.id.iv_go_top);
        NestedScrollActivity activity = (NestedScrollActivity) getActivity();
        mMnsv = activity.getMnsv();
        Log.i("weilgu","-----------------------------mnsv = " + mMnsv);
        mChild.setLayoutManager(new LinearLayoutManager(getContext()));
        mChild.setAdapter(new ChildAdapter());
        mChild.addOnScrollListener(new RecyclerView.OnScrollListener() {

            int totalScrollY = 0;
            boolean isScrllUp = false;

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.i("weilgu","onScrollStateChanged");
                RecyclerView.LayoutManager manager = mChild.getLayoutManager();
                if (manager instanceof LinearLayoutManager){
                    int itemCount = manager.getItemCount();
                    int itemPosition = ((LinearLayoutManager) manager).findLastVisibleItemPosition();
                    int firstVisibleItemPosition = ((LinearLayoutManager) manager).findFirstVisibleItemPosition();
                    ChildAdapter adapter = (ChildAdapter) mChild.getAdapter();
                    List<ChildMultiType> datas = adapter.getDatas();
                    if (itemCount - 1 == itemPosition) {
                        if (newState == RecyclerView.SCROLL_STATE_IDLE){
                            boolean isAddLoadMore = true;
                            for (int i = 0; i < datas.size(); i++) {
                                if (datas.get(i).getType() == 2){
                                    isAddLoadMore = false;
                                }
                            }
                            if (isAddLoadMore){
                                datas.add(datas.size(),new ChildMultiType(2));
                                adapter.notifyDataSetChanged();
                                loadMore(adapter);
                            }
                        }
                    }
                    if (newState == RecyclerView.SCROLL_STATE_IDLE && isScrollTop && firstVisibleItemPosition == 0){
                        isScrollTop = false;
                        if (mMnsv != null){
                            mMnsv.scrollTo(0,0);
                        }
                        //Log.i("weilgu","if (recyclerView.getTop() == 0 && newState == RecyclerView.SCROLL_STATE_IDLE)");
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalScrollY += dy;
                isScrllUp = dy > 0;
                Log.i("weilgu","onScrolled ***************** dy " + dy + "    totalScrollY = " + totalScrollY);
                if (totalScrollY > 500){ //显示回到顶部
                    if (mGoTop.getVisibility() == View.GONE){
                        mGoTop.setVisibility(View.VISIBLE);
                    }
                }else {
                    if (mGoTop.getVisibility() == View.VISIBLE){
                        mGoTop.setVisibility(View.GONE);
                    }
                }
            }
        });
        mGoTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandlers.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mGoTop.setVisibility(View.GONE);
                    }
                },300);
                isScrollTop = true;
                mChild.smoothScrollToPosition(0);
            }
        });
    }

    private void loadMoreFinish(ChildAdapter adapter){
        List<ChildMultiType> datas = adapter.getDatas();
        int removeIndex = -1;
        for (int i = 0; i < datas.size(); i++) {
            if (datas.get(i).getType() == 2){
                removeIndex = i;
            }
        }
        if (removeIndex != -1){
            datas.remove(removeIndex);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void loadMore(final ChildAdapter adapter) {
        mHandlers.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadMoreFinish(adapter);
                List<ChildMultiType> datas = adapter.getDatas();
                for (int i = 0; i < 10; i++) {
                    datas.add(new ChildMultiType(1));
                }
                adapter.notifyDataSetChanged();
            }
        },1500);
    }

    class ChildAdapter extends RecyclerView.Adapter{

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
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //item_load_more_layout
            if (viewType == 1){
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_child_rv, parent, false);
                return new ChildeHolder(view);
            }else{
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_load_more_layout, parent, false);
                return new LoadMoreHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (getItemViewType(position) == 1){
                ChildeHolder childeHolder = (ChildeHolder) holder;
                childeHolder.mTv.setText("childItem" + position);
            }
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        class ChildeHolder extends RecyclerView.ViewHolder{

            private final TextView mTv;

            public ChildeHolder(@NonNull View itemView) {
                super(itemView);
                mTv = itemView.findViewById(R.id.tv_child_rv_item);
            }
        }

        class LoadMoreHolder extends RecyclerView.ViewHolder {

            public LoadMoreHolder(@NonNull View itemView) {
                super(itemView);

            }
        }

        public List<ChildMultiType> getDatas() {
            return mDatas;
        }
    }

}

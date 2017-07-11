package cn.gongyuhua.blogs;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by gyh on 17-5-31.
 */

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //获取从Activity中传递过来每个item的数据集合
    private List<String> mDatas;
    Context mcontext;
    public final int item=4;
    //构造函数
    public MyAdapter(List<String> list,Context mcontext){
        this.mcontext=mcontext;
        this.mDatas = list;
    }


    public void update(List<String> list){
        this.mDatas=list;
        this.notifyDataSetChanged();
    }
    //创建View，如果是HeaderView或者是FooterView，直接在Holder中返回
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.post, parent, false);
        return new ListHolder(layout);
    }

    //绑定View，这里是根据返回的这个position的类型，从而进行绑定的，   HeaderView和FooterView, 就不同绑定了
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //这里加载数据的时候要注意，是从position-1开始，因为position==0已经被header占用了

        ((ListHolder) holder).title.setText(mDatas.get(item*position));
        ((ListHolder) holder).context.setText(Html.fromHtml(mDatas.get(item*position+1)));
        ((ListHolder) holder).data.setText(Html.fromHtml(mDatas.get(item*position+2)));
        ((ListHolder) holder).view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mcontext,PostActivity.class);
                intent.putExtra("id",mDatas.get(item*position+3));
                intent.putExtra("title",mDatas.get(item*position));
                mcontext.startActivity(intent);
                Log.d("ID",mDatas.get(item*position+3));
            }
        });
    }
    //在这里面加载ListView中的每个item的布局
    class ListHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView context;
        TextView data;
        TextView view;
        public ListHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.title);
            context = (TextView)itemView.findViewById(R.id.context);
            data = (TextView)itemView.findViewById(R.id.date);
            view = (TextView)itemView.findViewById(R.id.view);
        }
    }

    //返回View中Item的个数，这个时候，总的个数应该是ListView中Item的个数加上HeaderView和FooterView
    @Override
    public int getItemCount() {
            return mDatas.size()/item;
    }
}

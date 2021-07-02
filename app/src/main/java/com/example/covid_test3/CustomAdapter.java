package com.example.covid_test3;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

// 어뎁터에서 데이터를 가져옴 리스트뷰마다 어뎁터연결해줘야함
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ArrayList<Contents> arrayList;  // 유저 정보를 담게되는 arraylist
    private Context context;    // 선택한 액티비티에 대한 컨텍스트가 필요할 때 이용

    // Item의 클릭 상태를 저장할 array 객체
    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    // 직전에 클릭됐던 Item의 position
    int prePosition = -1;

    public CustomAdapter(ArrayList<Contents> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {   // 뷰 홀더를 최초로 만들어냄
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {  // 각 아이템들에 대한 매칭
        holder.onBind(arrayList.get(position), position, selectedItems);
        holder.setOnCustomItemClickListener(new OnCustomItemClickListener() {
            @Override
            public void onItemClick() {
                if (selectedItems.get(position)) {
                    // 펼쳐진 Item을 클릭 시
                    selectedItems.delete(position);
                } else {
                    // 직전의 클릭됐던 Item의 클릭상태를 지움
                    selectedItems.delete(prePosition);
                    // 클릭한 Item의 position을 저장
                    selectedItems.put(position, true);
                }
                // 해당 포지션의 변화를 알림
                if (prePosition != -1) notifyItemChanged(prePosition);
                notifyItemChanged(position);
                // 클릭된 position 저장
                prePosition = position;
            }
        });
    }

    @Override
    public int getItemCount() {
        // 삼항 연산자
        return (arrayList != null ? arrayList.size() : 0);
    }

    void addItem(Contents contents) {
        // 외부에서 item을 추가시킬 함수입니다.
        arrayList.add(contents);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        // list_item 에서 만든 얘들을 불러올꺼
        TextView tv_title;
        TextView tv_contents;
        TextView tv_date;
        TextView tv_address;



        LinearLayout linearlayout;
        OnCustomItemClickListener onCustomItemClickListener;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_title = itemView.findViewById(R.id.tv_title);   // 상속을 받았기 때문
            this.tv_contents = itemView.findViewById(R.id.tv_contents);
            this.tv_date = itemView.findViewById(R.id.tv_date);
            this.tv_address = itemView.findViewById(R.id.tv_address);
            this.linearlayout = itemView.findViewById(R.id.id_layout);



            linearlayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onCustomItemClickListener.onItemClick();
                }
            });


            linearlayout.setOnCreateContextMenuListener(this);


        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){

            MenuItem Delete = menu.add(Menu.NONE,1001,1,"삭제");
            Delete.setOnMenuItemClickListener(onEditMenu);

        }


        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item){
                if (item.getItemId() == 1001) {
                    database = FirebaseDatabase.getInstance(); // 파이어베이스 데이터베이스 연동
                    database.getReference("Contents").child(arrayList.get(prePosition).getTitle()).removeValue();

                    arrayList.remove(getBindingAdapterPosition());
                    notifyItemRemoved(getBindingAdapterPosition());
                    notifyItemChanged(getBindingAdapterPosition(), arrayList.size());
                }
                return true;
            }
        };


        void onBind(Contents contents, int position, SparseBooleanArray selectedItems){
            tv_title.setText(arrayList.get(position).getTitle());
            tv_contents.setText(arrayList.get(position).getContents());
            tv_date.setText(arrayList.get(position).getDate());
            tv_address.setText(arrayList.get(position).getAddress());

            changeVisibility(selectedItems.get(position));
        }

        public void changeVisibility(final boolean isExpanded) {
            // ValueAnimator.ofInt(int... values)는 View가 변할 값을 지정, 인자는 int 배열
            ValueAnimator va = isExpanded ? ValueAnimator.ofInt(0, 600) : ValueAnimator.ofInt(600, 0);
            // Animation이 실행되는 시간, n/1000초
            va.setDuration(500);
            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    // imageView의 높이 변경
                    tv_contents.getLayoutParams().height = (int) animation.getAnimatedValue();  // value는 height 값
                    tv_contents.requestLayout();
                    // imageView가 실제로 사라지게하는 부분
                    tv_contents.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                }
            });
            // Animation start
            va.start();
        }

        public void setOnCustomItemClickListener(OnCustomItemClickListener onCustomItemClickListener){
            this.onCustomItemClickListener = onCustomItemClickListener;
        }

    }
}

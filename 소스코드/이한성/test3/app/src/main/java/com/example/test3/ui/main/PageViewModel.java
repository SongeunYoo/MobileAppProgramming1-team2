package com.example.test3.ui.main;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class PageViewModel extends ViewModel {

    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private LiveData<String> mText = Transformations.map(mIndex, new Function<Integer, String>() {
        @Override
        public String apply(Integer input) {
            /*switch(input) {
                case 1:
                    return "전체 음식점 목록을 출력합니다";
                case 2:
                    return "한식 음식점 목록을 출력합니다";
                case 3:
                    return "양식 음식점 목록을 출력합니다";
                case 4:
                    return "중식 음식점 목록을 출력합니다";
                case 5:
                    return "기타 음식점 목록을 출력합니다";
                    default: return "목록을 출력합니다";
            }*/
            return "";
        }
    });

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public LiveData<String> getText() {
        return mText;
    }
}
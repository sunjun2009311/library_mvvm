package me.goldze.mvvmhabit.binding.databind;

import androidx.annotation.Nullable;
import androidx.databinding.ObservableField;

/**
 * @author: Jun
 * @date: 2023/2/17
 */
public class ObservableString extends ObservableField<String> {
    public ObservableString() {
        this("");
    }

    public ObservableString(String value) {
        super(value);
    }

    @Nullable
    @Override
    public String get() {
        return super.get();
    }
}

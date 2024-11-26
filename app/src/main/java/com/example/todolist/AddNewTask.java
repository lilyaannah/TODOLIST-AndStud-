package com.example.todolist;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todolist.model.ToDoModel;
import com.example.todolist.util.DataBaseHelper;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddNewTask extends BottomSheetDialogFragment {

    public static final String TAG = "AddNewTask";

    //widgets
    private EditText mEditText;
    private EditText mEditTextDescription;
    private Button mSaveButton;

    private DataBaseHelper myDb;

    public static AddNewTask newInstance(){
        return new AddNewTask();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_newtask , container , false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mEditText = view.findViewById(R.id.edittext);
        mEditTextDescription=view.findViewById(R.id.edittextDescription);
        mSaveButton = view.findViewById(R.id.button_save);

        myDb = new DataBaseHelper(getActivity());

        boolean isUpdate = false;

        final Bundle bundle = getArguments();
        if (bundle != null){
            isUpdate = true;
            String task = bundle.getString("task");
            String description=bundle.getString("description");
            mEditText.setText(task);
            mEditTextDescription.setText(description);

            if (task.length() > 0 ){
                mSaveButton.setEnabled(false);
            }

        }

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if ((mEditText.getText().toString().isEmpty() && mEditTextDescription.getText().toString().isEmpty())
                        // || mEditText.getText().toString().trim().isEmpty()
                ) {
                    mSaveButton.setEnabled(false);
                    mSaveButton.setBackgroundColor(Color.GRAY);
                } else {
                    mSaveButton.setEnabled(true);
                    mSaveButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        mEditText.addTextChangedListener(textWatcher);
        mEditTextDescription.addTextChangedListener(textWatcher);

        final boolean finalIsUpdate = isUpdate;
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = mEditText.getText().toString();
                String descriptionText = mEditTextDescription.getText().toString();

                if (finalIsUpdate) {
                    myDb.updateTask(bundle.getInt("id"), text, descriptionText);
                } else {
                    ToDoModel item = new ToDoModel();
                    item.setTask(text);
                    item.setDescription(descriptionText);
                    item.setStatus(0);
                    myDb.insertTask(item);
                }
                dismiss();
            }
        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if (activity instanceof OnDialogCloseListner){
            ((OnDialogCloseListner)activity).onDialogClose(dialog);
        }
    }

    // Метод для проверки наличия недопустимых символов
    private boolean containsInvalidCharacters(String text) {
        // Проверяем, есть ли пробелы или буквы
        return text.matches(".*[a-zA-Z\\s].*");
    }

    /////
//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        // Сохраняем текущее состояние полей ввода
//        outState.putString("task", mEditText.getText().toString());
//        outState.putString("description", mEditTextDescription.getText().toString());
//    }
//
//    @Override
//    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
//        super.onViewStateRestored(savedInstanceState);
//        if (savedInstanceState != null) {
//            // Восстанавливаем данные из сохраненного состояния
//            String savedTask = savedInstanceState.getString("task");
//            String savedDescription = savedInstanceState.getString("description");
//
//            mEditText.setText(savedTask);
//            mEditTextDescription.setText(savedDescription);
//
//            // Проверяем, если оба поля пустые, отключаем кнопку
//            if (savedTask != null && !savedTask.isEmpty() || savedDescription != null && !savedDescription.isEmpty()) {
//                mSaveButton.setEnabled(true);
//                mSaveButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//            } else {
//                mSaveButton.setEnabled(false);
//                mSaveButton.setBackgroundColor(Color.GRAY);
//            }
//        }
//    }
}
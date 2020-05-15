package com.example.a422androidpetukhova_sample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Адаптер - мост между данными и создаваемыми и управляемыми им View
 */
class ItemsDataAdapter extends BaseAdapter {

    // Хранит список всех элементов списка
    private List<ItemData> items;

    // LayoutInflater – класс, который из
    // layout-файла создает View-элемент.
    private LayoutInflater inflater;

    // Слушает все изменения галочки и меняет
    // состояние конкретного ItemData
    private View.OnClickListener btnDeleteListener // изменяется когда нажимаем чекбокс, он один для всех
            = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            removeItem(position);
        }
//        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//            items.get((Integer) buttonView.getTag()).setChecked(isChecked);
//        }
    };


    // Конструктор, в который передается контекст
    // для создания контролов из XML. И первоначальный список элементов.
    ItemsDataAdapter(Context context, List<ItemData> items) {
        if (items == null) {
            this.items = new ArrayList<>(); //если данные пустые , то создаем новый список
        } else {
            this.items = items;
        }
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); // для инфлейта наших данных используем инфлейтер
    }

    // Добавляет элемент в конец списка.
    // notifyDataSetChanged сообщает об обновлении данных и переотрисовывает.
    // Вы можете как угодно менять items в других местах.
    // Но не забывайте вызывать notifyDataSetChanged чтобы все обновилось.
    void addItem(ItemData item) {
        this.items.add(item); // добавляем новый датакласс
        notifyDataSetChanged();
    }

    // Удаляет элемент списка.
    void removeItem(int position) {
        items.remove(position);
        notifyDataSetChanged();
    }

    // Обязательный метод абстрактного класса BaseAdapter.
    // Он возвращает колличество элементов списка.
    @Override
    public int getCount() {
        return items.size(); //передаем длину нашего списка, допуситм у нас список из 10 элементов
    }

    // Тоже обязательный метод.
    // Должен возвращать элемент списка на позиции - position
    @Override
    public ItemData getItem(int position) { // передается позиция и надо возвратить нашу дату по позиции.
        if (position < items.size()) {
            return items.get(position);
        } else {
            return null;
        }
    }

    // И это тоже обязательный метод.
    // Возвращает идентификатор. Обычно это position.
    @Override
    public long getItemId(int position) {
        return position;
    }

    // Самый интересный обязательный метод.
    // Создает или возвращает переиспользуемый View, с новыми данными
    // для конкретной позиции. BaseAdapter – хитрый класс,
    // он не держит в памяти все View - это дорого и будет тормозить.
    // Поэтому он рисует только то что видно. Для этого есть convertView.
    // Если нет чего переиспользовать, то создается новый View.
    // А потом напоняет старую или новую View нужными данными.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.item_list_view, parent, false);
        }

        ItemData itemData = items.get(position);

        ImageView image = view.findViewById(R.id.icon);
        TextView title = view.findViewById(R.id.title);
        TextView subtitle = view.findViewById(R.id.subtitle);
        Button btnDelete = view.findViewById(R.id.btnDelete);

        image.setImageDrawable(itemData.getImage());
        title.setText(itemData.getTitle());
        subtitle.setText(itemData.getSubtitle());
        btnDelete.setOnClickListener(btnDeleteListener); // один листенер для всех чебоксов
        btnDelete.setTag(position); // с попощью данного метода передаем нашу позицию  в getTag.setChecked  и изменяем его состояние
        // checkBox.setChecked(itemData.isChecked());

        return view;
    }
}
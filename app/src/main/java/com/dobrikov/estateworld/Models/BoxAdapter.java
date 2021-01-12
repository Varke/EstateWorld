package com.dobrikov.estateworld.Models;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.dobrikov.estateworld.R;

public class BoxAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Apartment> objects;

    public BoxAdapter(Context context, ArrayList<Apartment> products) {
        ctx = context;
        objects = products;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // кол-во элементов
    @Override
    public int getCount() {
        return objects.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item, parent, false);
        }

        Apartment p = getProduct(position);

        // заполняем View в пункте списка данными из товаров: наименование, цена
        // и картинка
        ((TextView) view.findViewById(R.id.apartmentTitle)).setText(p.getTitle());
        ((TextView) view.findViewById(R.id.apartmentCost)).setText(p.getCost() + "₽");
        ((TextView) view.findViewById(R.id.apartmentCountRoomsAndMeters)).setText(p.getRoomsString() + ", " + p.getSize() + "кв.м.");
        ((TextView) view.findViewById(R.id.apartmentDescription)).setText(p.getCity() + ", " + p.getDistrict() + ", " + p.getStreet());
        ((ImageView) view.findViewById(R.id.apartmentImage)).setImageResource(p.getImage());

        return view;
    }

    // товар по позиции
    Apartment getProduct(int position) {
        return ((Apartment) getItem(position));
    }

    /* содержимое корзины
    ArrayList<Product> getBox() {
        ArrayList<Product> box = new ArrayList<Product>();
        for (Product p : objects) {
            // если в корзине
            if (p.box)
                box.add(p);
        }
        return box;
    }

    // обработчик для чекбоксов
    OnCheckedChangeListener myCheckChangeList = new OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            // меняем данные товара (в корзине или нет)
            getProduct((Integer) buttonView.getTag()).box = isChecked;
        }
    };

     */
}

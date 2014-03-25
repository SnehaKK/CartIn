package com.example.carte;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ItemDetailsBaseAdapter extends BaseAdapter {
	private static ArrayList<ItemDetails> itemDetailsArrayList;
	private LayoutInflater l_Inflater;

	public int getCount() {
		return itemDetailsArrayList.size();
	}

	public ItemDetailsBaseAdapter(Context context,
			ArrayList<ItemDetails> itemList) {
		super();
		itemDetailsArrayList = itemList;
		l_Inflater = LayoutInflater.from(context);
	}

	public Object getItem(int position) {
		return itemDetailsArrayList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = l_Inflater.inflate(R.layout.item_list_layout, null);
			holder = new ViewHolder();
			holder.txt_itemName = (TextView) convertView
					.findViewById(R.id.itemName);
			holder.txt_itemQuantity = (TextView) convertView
					.findViewById(R.id.quantity);
			holder.txt_itemPrice = (TextView) convertView
					.findViewById(R.id.price);
			holder.txt_itemAmount = (TextView) convertView
					.findViewById(R.id.amount);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.txt_itemName.setText(itemDetailsArrayList.get(position)
				.getItemName());
		holder.txt_itemQuantity.setText(Integer.toString(itemDetailsArrayList
				.get(position).getQuantity()));
		holder.txt_itemPrice.setText(itemDetailsArrayList.get(position)
				.getPrice().toString());
		holder.txt_itemAmount.setText(itemDetailsArrayList.get(position)
				.getAmount().toString());

		Log.i("info",
				"Position: "
						+ position
						+ "ItemName: "
						+ itemDetailsArrayList.get(position).getItemName()
						+ "Quantity: "
						+ Integer.toString(itemDetailsArrayList.get(position)
								.getQuantity()) + "Price: "
						+ holder.txt_itemPrice.getText() + "Amount: "
						+ holder.txt_itemPrice.getText() + "amt from holder:"
						+ holder.txt_itemAmount.getText());
		return convertView;
	}

	static class ViewHolder {
		TextView txt_itemName;
		TextView txt_itemQuantity;
		TextView txt_itemPrice;
		TextView txt_itemAmount;
	}
}

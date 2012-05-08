package cc.rep;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Collection implements Parcelable {
	private String name;
	private long id;
	private List<Item> items = new ArrayList<Item>();
	private List<Sharer> sharers = new ArrayList<Sharer>();;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection(Parcel in){
		// the parcel is read FIFO
		this.name = in.readString();
		this.id = in.readLong();
		in.readTypedList(items, Item.CREATOR);
		in.readTypedList(sharers, Sharer.CREATOR);
	}
	
	public Collection(){
		items = new ArrayList<Item>();
	}
	
	public long getID(){
		return id;
	}
	
	public void setID(long id){
		this.id = id;
	}

	public void addItem(Item item){
		items.add(item);
	}
	public void addSharer(Sharer sharer){
		sharers.add(sharer);
	}
	public boolean removeItem(Item item){
		return items.remove(item);
	}
	public boolean removeSharer(Sharer sharer){
		return sharers.remove(sharer);
	}
	

	public List<Item> getItems(){
		return items;
	}
	public List<Sharer> getSharers(){
		return sharers;
	}

	//@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	//@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(name);
		dest.writeLong(id);
		dest.writeTypedList(items);
		dest.writeTypedList(sharers);
	}
	
	// used to regenerate object
	public static final Parcelable.Creator<Collection> CREATOR = new Parcelable.Creator<Collection>(){
		public Collection createFromParcel(Parcel in){
			return new Collection(in);
		}
		
		public Collection[] newArray(int size){
			return new Collection[size];
		}
	};
	
	

}

package paperplane.android.me.aars.paperplane.GUI;

import android.graphics.Canvas;
import java.util.ArrayList;

public class Panel2D extends Component2D {

	public ArrayList<Component2D> components;
	
	public Panel2D() {
		components = new ArrayList<Component2D>();
	}
	
	public void add(Component2D c) {
		components.add(c);
	}


	public void removeAllComponents() {
		components.clear();
	}
	
	public void draw(Canvas c) {
		if(components.size()<=0) return;
		if(!isVisible) return;

		if(components.size()==1) {
			components.get(0).draw(c);
			return;
		}

		for(int i = 0; i<components.size(); i++)
			components.get(i).draw(c);
	}
	
	public boolean isHit(int x, int y, int type, int button) {
		if(components.size()<=0) return false;
		if(!isVisible) return false;
		for(int i = components.size()-1; i>=0; i--) {
			if(components.get(i).isHit(x, y, type, button)) return true;
		}
		return false;
	}
	
	public void update() {
		if(components.size()<=0) return;
		for(int i = 0; i<components.size(); i++) {
			components.get(i).update();
		}
	}
}
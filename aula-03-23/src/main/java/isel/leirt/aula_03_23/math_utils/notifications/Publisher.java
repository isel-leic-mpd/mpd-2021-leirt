package isel.leirt.aula_03_23.math_utils.notifications;

public interface Publisher {
	void subscribe(Subscriber s);
	void unsubscribe(Subscriber s);
	void notifyChange();
}

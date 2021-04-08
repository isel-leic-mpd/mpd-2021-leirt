package isel.leirt.mpd;

public class ReflexUtils {

	/**
	 * This method behaves similar to instanceof java operator
	 * @param obj
	 * @param type
	 * @return
	 */
	public static boolean instanceOf(Object obj, Class<?> type) {
		Class<?> cls = obj.getClass();


		if (obj == null) return false;


		if (!type.isInterface()) {
			// check the class hierarchy
			while (cls != null) {
				if (cls == type) return true;
				cls = cls.getSuperclass();
			}
		}
		else {
			// check if the interface is implemented
			// this implementation is incompleted!
			// we should check the interfaces implemented in all the class hierarchy
			for (Class<?> i : cls.getInterfaces()) {
				if (i == type) return true;
			}
		}
		return false;

	}
}


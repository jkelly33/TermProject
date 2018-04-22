
public class LLTest {

	public static void main(String[] args) {
		Queue queue = new Queue();
		Room room1 = new Room("XXRoom1");
		Room room2 = new Room("XXRoom2");
		Room room3 = new Room("XXRoom3");
		Room room4 = new Room("XXRoom4");
		Room room5 = new Room("XXRoom5");
		queue.enqueue(room1);
		queue.enqueue(room2);
		queue.enqueue(room3);
		queue.enqueue(room4);
		queue.enqueue(room5);
		Room room = queue.dequeue();
		System.out.println(room.value);
		Room room23 = queue.dequeue();
		System.out.println(room23.value);
		Room room434 = queue.dequeue();
		System.out.println(room434.value);
		Room room34 = queue.dequeue();
		System.out.println(room34.value);
		
		

	}

}

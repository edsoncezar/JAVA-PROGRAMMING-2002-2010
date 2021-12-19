public class MaxPriorityTest {
    public static void main(String[] args) {

        ThreadGroup groupNORM = new ThreadGroup(
                                    "A group with normal priority");
        Thread priorityMAX = new Thread(groupNORM, 
                                 "A thread with maximum priority");

        // set Thread's priority to max (10)
        priorityMAX.setPriority(Thread.MAX_PRIORITY);

        // set ThreadGroup's max priority to normal (5)
        groupNORM.setMaxPriority(Thread.NORM_PRIORITY);

        System.out.println("Group's maximum priority = " +
			   groupNORM.getMaxPriority());
        System.out.println("Thread's priority = " +
			   priorityMAX.getPriority());
    }
}

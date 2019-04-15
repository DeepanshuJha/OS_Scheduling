import java.util.*;

public class bankers{

    static void isSafe(int n_process, int n_resource, int allocation[][], int max_need[][], int available[]){

        int cur_available[] = new int[n_resource];

        for(int i = 0; i < n_resource; i++){
            cur_available[i] = available[i];
        }

        for(int i = 0; i < n_process; i++){
            for(int j = 0; j < n_resource; j++){
                cur_available[j] -= allocation[i][j];
            }
        }

        int need[][] = new int[n_process][n_resource];
        for(int i = 0; i < n_process; i++){
            for(int j = 0; j < n_resource; j++){
                need[i][j] = max_need[i][j] - allocation[i][j];
            }
        }

        int process_squence[] = new int[n_process];
        int process_cnt = 0;

        for(int k = 0; k < n_process; k++){

            for(int i = 0; i < n_process; i++){
                int cnt_check = 0;
                for(int j = 0; j < n_resource; j++){
                    if(cur_available[j] >= need[i][j] && need[i][j] > -1){ //check
                        cnt_check++;
                    }
                }
                if(cnt_check == n_resource){
                    process_squence[process_cnt] = i;
                    process_cnt++;
                    for(int j = 0; j < n_resource; j++){
                        cur_available[j] += allocation[i][j];
                        need[i][j] = -1;
                    }
                }
            }
            if(process_cnt == n_process){
                System.out.println("System is in Safe State");
                System.out.print("Process Sequence : ");
                for(int i = 0; i < process_cnt - 1; i++){
                    System.out.print(process_squence[i]+" -> ");
                }
                System.out.println(process_squence[process_cnt - 1]);
                return;
            }
        }
        if(process_cnt != n_process){
            System.out.println("System is not in Safe State " + process_cnt);
        }
    }
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);

        int n_process, n_resource;

        System.out.print("No of Processes : ");
        n_process = sc.nextInt();

        System.out.print("No of Resources : ");
        n_resource = sc.nextInt();

        int allocation[][] = new int[n_process][n_resource];
        System.out.println("Enter Allocation Matrix : ");
        for(int i = 0; i < n_process; i++){
            for(int j = 0; j < n_resource; j++){
                allocation[i][j] = sc.nextInt();
            }
        }

        int max_need[][] = new int[n_process][n_resource];
        System.out.println("Enter Maximum Need Matrix : ");
        for(int i = 0; i < n_process; i++){
            for(int j = 0; j < n_resource; j++){
                max_need[i][j] = sc.nextInt();
            }
        }

        int available[] = new int[n_resource];
        System.out.println("Enter Total Available Resource : ");
        for(int i = 0; i < n_resource; i++){
            available[i] = sc.nextInt();
        }

        isSafe(n_process, n_resource, allocation, max_need, available);
    }
}

/*
5
3
Allocation
0 1 0
2 0 0
3 0 2
2 1 1
0 0 2

Max-need 
7 5 3
3 2 2
9 0 2
4 2 2
5 3 3

Total resources 
10 5 7
*/
import java.util.*;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class Mafia {
    public static void main(String[] args) {
        // Initialize scanner for user input
        Scanner scan = new Scanner(System.in);
        // Initialize RNG for role assignment
        Random rand = new Random();
        // number of players
        int numPlayer;
        // hashmap: player's name, role
        HashMap<String, String> players = new HashMap<String, String>();
        // list of roles
        String[] roleArr = {"마피아", "평민", "경찰", "의사"};
        ArrayList<String> roles = new ArrayList<String>();
        for(String r: roleArr){
            roles.add(r);
        }


        JFrame f = new JFrame("The Mafia");
        f.setSize(500, 500);
        f.setLocation(300,200);
        final JTextArea textArea = new JTextArea(10, 40);
        f.getContentPane().add(BorderLayout.CENTER, textArea);
        final JButton button = new JButton("Click Me");
        f.getContentPane().add(BorderLayout.SOUTH, button);
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.append("Button was clicked\n");

            }
        });

        f.setVisible(true);


        // Start Game by asking how many players there are
        System.out.println("몇명이 참여하나요?");
        numPlayer = scan.nextInt();

        // Register player and assign role
        for(int i=0; i<numPlayer; i++){
            String player;
            int display = i+1;

            // Obtain new player's name
            System.out.println(display+"번째 참가자의 코드네임을 적으시오: ");
            player = scan.next();

            // Obtain a number between [0 - roles.length-1].
            int ranRole = rand.nextInt(roles.size());
            String role = roles.get(ranRole);
            System.out.println("당신은: " + role);
            players.put(player, role);
            roles.remove(ranRole);
            // Press ENTER to continue, wipes out data from console
            pressAnyKeyToContinue();
        }

        int numNight=1;
        boolean gameOver = false;
        String nthDayMessage;
        // Mafia's target, Doctor's saved, Commoner's rubbish input
        String target = "";
        String saved = "";
        String rubbish;
        ArrayList<String> mafiaGuessList = new ArrayList<String>();
        String lynched = "";
        // Continue nights until Mafia is caught
        while(!gameOver){

            System.out.println(numNight + "번째 밤이 시작되었습니다. 첫번째 참가자부터 확인하겠습니다.");

            // n번째 밤 시나리오
            for(int j=0; j<players.size(); j++){
                String codeName = null;

                while(!players.containsKey(codeName)){
                    System.out.println("당신의 코드네임을 입력하시오: ");
                    codeName = scan.next();
                }

                if(players.get(codeName).equals("마피아")){
                    System.out.println("당신은 마피아입니다. 타깃의 코드네임을 적으시오: ");
                    target = scan.next();
                    pressAnyKeyToContinue();
                }
                else if(players.get(codeName).equals("평민")){
                    System.out.println("당신은 평민입니다. 평민인 것을 숨기기 위해 아무 단어를 적으시오: ");
                    rubbish = scan.next();
                    pressAnyKeyToContinue();
                }
                else if(players.get(codeName).equals("경찰")){
                    System.out.println("당신은 경찰입니다. 조사할 참가자의 코드네임을 적으시오: ");
                    String testify = scan.next();
                    if(players.get(testify).equals("마피아")){
                        System.out.println(testify + "는(은) 마피아입니다.");
                    }
                    else{
                        System.out.println(testify + "는(은) 마피아가 아닙니다.");
                    }
                    pressAnyKeyToContinue();
                }
                else if(players.get(codeName).equals("의사")){
                    System.out.println("위험에 처했다면 구하고 싶은 참가자(본인포함)의 코드네임을 적으시오: ");
                    saved = scan.next();
                    pressAnyKeyToContinue();
                }
            }

            // 상황 정리
            if(!target.equals("") && !saved.equals("") && target.equalsIgnoreCase(saved)){
                System.out.println("어젯밤 " + target + "은(는) 마피에게 저격당했으나 의사가 기적적으로 살려냈습니다. 즉, 아무도 죽지 않았습니다. 첫번째 참가자부터 ");
            }
            else{
                System.out.println("어젯밤 " + target + "은(는) 마피아에게 저격당했습니다. 첫번째 참가자부터 ");
                players.remove(target);
            }

            // 마피아 토론 후 린치 투표
            for(int k=0; k<players.size(); k++){
                String suspect = null;
                while(!players.containsKey(suspect)){
                    System.out.println("마피아로 의심되는 참가자의 코드네임을 적으시오: ");
                    suspect = scan.next();
                }
                if(mafiaGuessList.contains(suspect)){
                    lynched = suspect;
                }
                else{
                    mafiaGuessList.add(suspect);
                }
                pressAnyKeyToContinue();
            }
            if(lynched.equals("")){
                System.out.println("과반수 이상의 투표로 마피아로 의심된 참가자가 없었습니다.");
            }
            else{
                System.out.println(lynched + "는(은) 과반수 이상의 투표로 마피아로 의심되어 죽고 말았습니다.");
            }

            // 린치 투표 결과
            if(!lynched.equals("") && players.get(lynched).equalsIgnoreCase("마피아")){
                gameOver = true;
                nthDayMessage = "마피아가 사라졌습니다. 축하합니다, 마을 사람들이 승리했습니다!";
            }
            else if(players.size() == 2){
                gameOver = true;
                nthDayMessage = "죄없는 사람을 죽이고 말았습니다. 그리고 안타깝게도 마피아가 승리했습니다.";
            }
            else{
                nthDayMessage = "죄없는 사람을 죽이고 말았습니다. 게임은 계속됩니다...";
                numNight++;
            }
            System.out.println(nthDayMessage);
        }
    }

    public static void pressAnyKeyToContinue(){
        System.out.println("당신의 할일이 끝났으니 Enter키를 누르고 옆사람에게 컴퓨터를 주시오.");
        try
        {
            System.in.read();
        }
        catch(Exception e)
        {}
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
package game;

import java.util.Random;
import java.util.Scanner;

public class Game {

	//マップをインスタンス化
	static boolean[][] map = new boolean[9][9];

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);

		//マップ生成
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map.length; j++) {
				map[i][j] = true;
			}
		}

		//ブロックの生成
		final boolean[][][] block = {
				{ { true, true, true } },

				{ { true, true },
				  { false, true } },

				{ { false, true },
				  { true, true } },

				{ { true, true },
				  { true, false } },

				{ { true, false },
				  { true, true } },

				{ { true, true },
				  { true, true } },

				{ { true },
				  { true },
				  { true }
				},

				{ { true },
			      { true },
				}
		};

		//ブロックのランダム表示用
		Random random = new Random();

		int score = 0;
		System.out.println("--------------------");
		System.out.println("Welcome to TETRiS !!");
		System.out.println("--------------------");

		//ゲームループ
		while (true) {
			int savex = 0; //ブロックの行ポジションのセーブ
			int stop = 0; //行ポジションの更新止め
			int num = 0; //入力変数
			int rand = random.nextInt(block.length);
			
			//落ちてくるブロックの表示
			System.out.println("\n落ちてくるブロック：");
			for (int i = 0; i < block[rand].length; i++) {
				for (int j = 0; j < block[rand][0].length; j++) {
					if (block[rand][i][j])
						System.out.print("□");
					else
						System.out.print("　");
				}
				System.out.println("");
			}

			//盤面表示
			view();
			
			//配置場所入力
			System.out.print("\n四角をどこにおく？（左端が先頭）　＞");
			String numPro = scanner.next();
			//数字以外の文字列は再入力
			try {
				num = Integer.parseInt(numPro);
			} catch (NumberFormatException e) {
				continue;
			}
			//１～９までの数字かつおける範囲ブロックのおける範囲でなければ再入力
			if (!(num > 0 && num < 10) || (num - 1 + block[rand][0].length) > map.length)
				continue;
			
			//ブロックの縦位置を格納
			int[] save = new int[block[rand][0].length];
			for (int i = 0; i < block[rand].length; i++) {
				for (int j = 0; j < block[rand][0].length; j++) {
					if (i != block[rand].length - 1) {
						if (!block[rand][i + 1][j])
							save[j] = i;
						else
							save[j] = i + 1;
					}
				}
			}
			
			//マップ上で落下可能か判定し、ポジションを決定
			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < block[rand][0].length; j++) {

					if (map[i + save[j]][j + num - 1]) {
						if (stop == 0)
							savex = i;
					} else {
						if (stop == 0) {
							savex = i - block[rand].length + 1;
							if (block[rand].length == 1) {
								savex--;
							}
							if (block[rand].length == 3) {
								savex++;
							}
							stop++;
						}
						break;
					}
				}
				if (map.length - 1 == (i + block[rand].length - 1))
					break;
			}
			
			//マップに配置できないとゲームオーバー
			if (savex < 0) {
				System.out.println("\n----------");
				System.out.println("Game Over");
				System.out.println("Score:" + score);
				System.out.println("----------");
				break;
			}
			score++;
			
			//配置できるなら、マップに配置
			for (int i = 0; i < block[rand].length; i++) {
				for (int j = 0; j < block[rand][0].length; j++) {

					if (block[rand][i][j]) {
						map[savex + i][j + num - 1] = false;
					}
					//else map[savex+i][j+num-1]=true;
					//System.out.println(i+":"+j+" iのトップ位置:"+savex+"ブロックの縦幅の位置："+i+map[savex+i][j+num-1]+":"+block[1][i][j]);
				}
			}
			
			//一列揃ったら消す
			for (int i = 0; i < map.length; i++) {
				int count = 0;
				for (int j = 0; j < map.length; j++) {
					if (!map[i][j])
						count++;
				}
				if (count == map.length) {
					view(); //消す前の表示
					for (int j = i; 0 < j; j--) {
						for (int k = 0; k < map.length; k++)
							map[j][k] = map[j - 1][k];
					}
					for (int j = 0; j < map.length; j++)
						map[0][j] = true;
					score += 10;
				}
			}
		}
		scanner.close();
	}

	//マップ描画
	public static void view() {
		String frame = "----------------------------";
		System.out.println("\n １ ２ ３ ４ ５ ６ ７ ８ ９");
		System.out.println(frame);
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map.length; j++) {
				if (!map[i][j]) {
					System.out.print("|□");
				} else
					System.out.print("|　");
			}
			System.out.println("|");
			System.out.println(frame);
		}
	}
}

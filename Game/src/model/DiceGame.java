package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class DiceGame {
	public int numDice = 5;
	int numImgs = 20;
	public String diceStory;
	int animNum;
	Die[] dice;
	public int[] imgNums;
	boolean[] imgBools;
	int xIncr;
	int screenWidth;
	int screenHeight;
	int diceWidth;
	int betweenDice;
	int betweenStory;
	int diceStartX;
	int diceStartY;
	String[] storyEntered;

	// Story Stuff
	private ArrayList<String> curseWords; // = new ArrayList<String>();
	private String curseWord = "";

	public DiceGame(int sWidth, int sHeight, int dWidth) {
		dice = new Die[numDice];
		imgNums = new int[numDice];
		imgBools = new boolean[numImgs];
		screenWidth = sWidth;
		screenHeight = sHeight;
		diceWidth = dWidth;
		betweenDice = diceWidth + (screenWidth / 60);
		diceStartX = (screenWidth - (numDice / 2 * diceWidth + (numDice / 2 - 1) * betweenDice)) / 2;
		diceStartY = (screenHeight - (4 * diceWidth + 2 * betweenStory)) / 2;
		
		/*initializing curse words*/
		String tempString = new String("anal anilingus anus arse arsehole ass asses assfucker asshole assholes assmucus bullshit succ boobies succc devil bull goddamn damn gotdam weed pot assmunch asswhole autoerotic ballbag ballsack bareback bastard beastial beastiality bellend bestial bestiality biatch bimbo bimbos birdlock bitch bitcher bitchers bitches bitchin bitching bloody blow blowjob blowjobs blumpkin boiolas bollock bollok boner boob boobs booobs boooobs booooobs booooooobs breasts buceta bugger bum bust busty butt buttfuck butthole buttmuch buttplug carpetmuncher cawk chink choade cipa clit clitlicker clitoris clits clittylitter clusterfuck cnut cock cockpocket cocksnot cockface cockhead cockmunch cockmuncher cocks cocksuck cocksucked cocksucker cocksucking cocksucks cocksuka cocksukka cok cokmuncher coksucka coon cornhole whore cox cum cumdump cummer cumming cums cumshot cunilingus cunillingus cunnilingus cunt cuntbag cuntlick cuntlicker cuntlicking cunts cuntsicle cyalis cyberfuc cyberfuck cyberfucked cyberfucker cyberfuckers cyberfucking damn dick dickhead dildo dildos dink dinks dirsa dlck doggiestyle doggin dogging donkeyribber doosh duche dyke ejaculate ejaculated ejaculates ejaculating ejaculatings ejaculation ejakulate erotic facial fag fagging faggitt faggot faggs fagot fagots fags fanny fannyflaps fannyfucker fanyy fatass fcuk fcuker fcuking feck fecker felching fellate fellatio fingerfuck fingerfucked fingerfucker fingerfuckers fingerfucking fingerfucks fistfuck fistfuck fistfucked fistfucker fistfuckers fistfucking fistfuckings fistfucks flange fook fooker fuck fucka fuckass fuckbitch fucked fucker fuckers fuckhead fuckheads fuckin fucking fuckings fuckingshitmotherfucker fuckme fuckmeat fucks fucktoy fuckwhit fuckwit fudgepacker fuk fuker fukker fukkin fuks fukwhit fukwit fux gangbang gangbanged gangbangs gaylord gaysex goatse god goddamn goddamned hardcoresex hell heshe hoar hoare hoer homo homoerotic hore horniest horny hotsex jackoff jap jerk jerkoff jism jiz jizm jizz kawk knob knobead knobed knobend knobhead knobjocky knobjokey kock kondum kondums kum kummer kumming kums kunilingus kwif labia lmao lmfao lust lusting masochist masterbat masterbate masterbation masterbations masturbate mofo mothafuck mothafucka mothafuckas mothafuckaz mothafucked mothafucker mothafuckers mothafuckin mothafucking mothafuckings mothafucks motherfuck motherfucked motherfucker motherfuckers motherfuckin motherfucking motherfuckings motherfuckka motherfucks muff mutha muthafecker muthafuckker muther mutherfucker nazi nigga niggah niggas niggaz nigger niggers nob nobhead nobjocky nobjokey numbnuts nutsack omg omfg orgasim orgasims orgasm orgasms pawn pecker penis penisfucker phonesex phuck phuk phuked phuking phukked phukking phuks phuq pigfucker pimpis piss pissed pisser pissers pisses pissflaps pissin pissing pissoff poop porn porno pornography pornos prick pricks pron pube pusse pussi pussies pussy pussys queaf queer rectum retard rimjaw rimjob rimming sadism sadist sandbar schlong screwing scroat scrote scrotum semen sex shag shagger shaggin shagging shemale shit shitfucker shitdick shite shited shitey shitfuck shitfull shithead shiting shitings shits shitted shitter shitters shitting shittings shitty skank slope slut sluts smegma smut snatch sonofabitch spac spunk teets teez testical testicle tit titfuck tits titt tittiefucker titties tittyfuck tittywank titwank tosser turd twat twathead twatty twunt twunter vagina viagra vulva wang wank wanker wanky whoar whore willies willy wtf xrated xxx");
		String[] tempArr = tempString.split(" ");
		curseWords = new ArrayList<String>(Arrays.asList(tempArr));
	}

	// Getters

	public int getNumDice() {
		return numDice;
	}

	public int getNumImgs() {
		return numImgs;
	}

	public String getDiceStory() {
		return diceStory;
	}

	public int getAnimNum() {
		return animNum;
	}

	public Die[] getDice() {
		return dice;
	}

	public Die getDieElem(int elem) {
		return dice[elem];
	}

	public String getCurseWord(){
		return curseWord;
	}
	
	// Setters
	public void setNumDice(int nd){
		numDice = nd;
	}
	
	public void setNumImgs(int ni){
		numImgs = ni;
	}
	
	public void diceSetter(Die[] darr){
		dice = darr;
	}
	
	public void setDieElem(Die d, int elem){
		dice[elem] = d;
	}
	
	public void setCurseWord(String curse){
		curseWord = curse;
	}
	
	public void setAnimNum(int a) {
		animNum = a;
	}
	
	public void setDiceStory(String s){
		diceStory = s;
	}

	// Makes array of length numDice signaling whether or not an image has been
	// chosen
	void setImgBools() {
		for (int i = 0; i < numImgs; i++) {
			imgBools[i] = false;
		}
	}

	public void setDice() {
		setImgBools();
		Random rand = new Random();
		int dieX = 0;
		int dieY = 0;

		for (int i = 0; i < numDice; i++) {
			if (i < 3) {
				dieX = diceStartX + (diceWidth + betweenDice) * i - (diceWidth + betweenDice) / 2;
				dieY = diceStartY;
				xIncr = 9;
			} else {
				dieX = diceStartX + (diceWidth + betweenDice) * (i % (numDice / 2));
				dieY = screenHeight - diceStartY - diceWidth;
				xIncr = 4;
			}
			Die tempDie = new Die(i, dieX, dieY, xIncr, diceWidth, screenWidth, screenHeight);
			int randInt = rand.nextInt(numImgs);
			while (imgBools[randInt]) {
				randInt = rand.nextInt(numImgs);
			}
			tempDie.dieImgNum = randInt;
			imgBools[randInt] = true;
			imgNums[i] = randInt;
			tempDie.setInitXLoc(dieX);
			tempDie.setInitYLov(dieY);
			dice[i] = tempDie;
		}
	} // makes all dice with images

	// Curse Word Checker
	/**
	 * writes profane words to a file (if new bad words are to be added)
	 *
	 * @throws IOException
	 */
	public void writeCurseWordsToFile() throws IOException {
		File file = new File("badWordsDice.tmp");
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(curseWords);
		oos.close();
	}

	/**
	 * reads into an arrayList a list of profane words
	 *
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void readCurseWordsFromFile() throws IOException, ClassNotFoundException {
		FileInputStream fis = new FileInputStream("badWordsDice.tmp");
		ObjectInputStream ois = new ObjectInputStream(fis);
		curseWords = (ArrayList<String>) ois.readObject();
		ois.close();
	}

	/**
	 * adds a new word to the arrayList of bad words
	 *
	 * @param s
	 */
	public void addCurseWordToList(String s) {
		curseWords.add(s);
	}

	/**
	 * loops through entire story and returns true if there is a curse word
	 * present, returns false otherwise
	 * 
	 * @return whether or not there is a curse word present
	 */
	public boolean isCurseWord() {
		for (String s : storyEntered) {
			if (curseWords.contains(s)) {
				curseWord = s;
				return true;
			}
		}
		return false;
	}

	/**
	 * Removes any characters that aren't letters from the string, and splits
	 * the string into an array of words
	 * 
	 * @param s
	 */
	public void separateStory(String s) {
		storyEntered = s.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
	}
}
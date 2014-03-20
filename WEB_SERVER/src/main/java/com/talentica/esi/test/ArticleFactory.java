package com.talentica.esi.test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ArticleFactory {
	private final static int[] delay = new int[4];
	static{
		Properties prop = new Properties();
		File file = new File("/tmp/article.properties");
		FileReader reader;
		try {
			reader = new FileReader(file);
			prop.load(reader);
			delay[0]=Integer.valueOf(prop.getProperty("heading"));
			delay[1]=Integer.valueOf(prop.getProperty("article1"));
			delay[2]=Integer.valueOf(prop.getProperty("article2"));
			delay[3]=Integer.valueOf(prop.getProperty("article3"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	public static Article getHeading() throws InterruptedException{
		Thread.sleep(delay[0]);
		return new Article("Hello, ESI Test Page!",
				"This is a esi template page for ESI performance test. The page " +
						"contains 4 esi templates. One horizonatal pagelet, describing this " +
						"template itself. Three vertical pagelets below this showing news " +
				"articles");
	}

	public static Article getArticle1() throws InterruptedException{
		Thread.sleep(delay[1]);
		return new Article("Nine phase Lok Sabha elections from April 7",
				"The Election Commission on Wednesday announced a nine-phase poll " +
						"for the 16th Lok Sabha from April 7 to May 12. The counting of votes " +
						"will be taken up on May 16. The model code of conduct has come into " + 
						"force with immediate effect. The Lok Sabha has a strength of 543 " +
						"(excluding two nominated members) and the term of the present 15th Lok " +
						"Sabha ends on May 31. The nine phases of polls are to be held on: " +
						"April 7, 9, 10, 12, 17, 24, 30, May 7 and May 12. The elections to the " +
						"294-member Andhra Pradesh Assembly will be held on April 30 and May 7 " +
						"along with the respective Lok Sabha constituencies in the State. It " +
						"will be on April 10 and 17 in Odisha for the Assembly/Lok Sabha and " +
						"April 12 for the Sikkim Assembly/Lok Sabha seat. The Odisha Assembly " +
				"has a strength of 147 members and the Sikkim Assembly 32 members.");
	}

	public static Article getArticle2() throws InterruptedException{
		Thread.sleep(delay[2]);
		return new Article("Comic book targets children in slums",
				"There are no superheroes in this comic book. There is no "+ 
						"âslumdog millionaireâ either. There are only messages for children "+
						"living in slums; to stay away from tobacco and alcohol, to know âbad "+
						"touchâ and to keep the environment clean, among others. âReach "+
						"out! With Veera and Cheraâ â a comic book with seven episodes in "+
						"it, by Loyola College student Vijay Asokan, was released at the "+
						"Outreach Dayâ2014 celebrations of the collegeâs department of "+
						"outreach, on Monday. It is a simple strip in the form of an "+
						"interaction between Veera, a Loyola college student who visits the "+
						"slums as part of the outreach programme, and Chera, a child from the "+
						"slum who handles challenges normally faced by children in slums in the "+
						"cities. âChildren in slums do not know the serious problems in life. "+
						"That is why the comic strip has been designed. Apart from creating "+
						"awareness on the harmful effects of tobacco and alcohol use, there is "+
				"also an episode explaining child abuse,â says Vijay.");

	}

	public static Article getArticle3() throws InterruptedException{
		Thread.sleep(delay[3]);
		return new Article("India restricts Bangladesh to 159",
				"Electing to bowl first, India bundled out Bangladesh for 159 in "+
						"their final league match of the Asia Cup on Wednesday. Brief scores: "+
						"Bangladesh: 159 all out in 45.2 overs (Samiullah Shenwari 50, Noor Ali "+
				"Zadran 31; Ravindra Jadeja 4/30, R. Ashwin 3/31).");

	}
}

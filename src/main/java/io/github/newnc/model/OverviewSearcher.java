package io.github.newnc.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.basic.BasicSplitPaneUI.KeyboardUpLeftHandler;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import io.github.newnc.util.KeyWordsList;

/*
	This class searches the overview of a movie for key-words
	and gives a genre to the movie based on the key-words present in it
*/
	
public class OverviewSearcher {
	
	private Document document;
	private Analyzer analyzer;
	private RAMDirectory directory;
	private IndexWriterConfig config;
	private IndexWriter writer;
	private IndexSearcher searcher;
	private QueryParser parser;
	
	public List<MovieInfo> execute(List<MovieInfo> movies) throws Exception{
		List<String> keywords_by_movie = new ArrayList<>();
		
		/*
		configAndPrepare(movies);
		@SuppressWarnings("deprecation") IndexReader reader = IndexReader.open(directory);
		searcher = new IndexSearcher(reader);
		*/
		
		/* find movie keywords */
		for(MovieInfo movie: movies){
			System.out.println(movie.stringify());
			keywords_by_movie.clear();
			
			String overview = movie.getOverview();
			for(String key_word: KeyWordsList.keyWords){
				//System.out.println(key_word);
				if(movie.getOverview().toUpperCase().contains(key_word)){
					keywords_by_movie.add(key_word);
					
					System.out.println("##########################");
					System.out.println("Movie "+movie.getTitle()+" has key-word "+key_word);
					System.out.println("##########################");
				}
				/*
				if (execute(overview, key_word)) {
					System.out.println("Add " + key_word + " to " + movie.getTitle());
					keywords_by_movie.add(key_word);
				}
				*/
			}
			
			/* classify movie genre */
			movie.setGenre(keywords_by_movie);
			
		}
		
		//reader.close();
		
		return movies;

	}
	
	private void configAndPrepare(List<MovieInfo> movies) throws IOException {
		/* Config */
		analyzer = new StandardAnalyzer();
	    // Store the index in memory:
	    directory = new RAMDirectory();
	    // To store an index on disk, use this instead:
	    //Directory directory = FSDirectory.open("/tmp/testindex");
	    config = new IndexWriterConfig(null, analyzer);
	    writer = new IndexWriter(directory, config);
	    
	    /* Prepare */
	    document = new Document();
		for (MovieInfo movie : movies)
			addOverviewToLuceneDocument(movie.getTitle(), movie.getOverview());
		
		parser = new QueryParser(Version.LUCENE_4_0, "title", analyzer);
		
		/* Closing */
	    writer.close();
	}
	
	@SuppressWarnings("deprecation")
	private void addOverviewToLuceneDocument(String title, String overview) throws IOException {
		Document document = new Document();
		
		// index movie's title
		Field titleField = new Field("title", title, Field.Store.YES, Field.Index.NOT_ANALYZED);
		// index movie's overview
		Field overviewField = new Field("overview", overview.getBytes());
		
		document.add(titleField);
		document.add(overviewField);
		
		writer.addDocument(document);
	}
	
	private boolean execute(String overview, String keyWord) throws Exception, ParseException {
		Term term = new Term(overview, keyWord);
		
		//Query query = new TermQuery(term);
		//Query query = new PrefixQuery(term);
		//Query query = new FuzzyQuery(term);
		Query query = parser.parse(keyWord);
		
		TopDocs docs = searcher.search(query, 1);
		
		return docs.totalHits == 1;
	}
}

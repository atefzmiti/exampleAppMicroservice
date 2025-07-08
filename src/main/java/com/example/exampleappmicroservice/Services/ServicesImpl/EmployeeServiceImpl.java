package com.example.exampleappmicroservice.Services.ServicesImpl;


import com.example.exampleappmicroservice.DAO.EmployeeDao;
import com.example.exampleappmicroservice.DAO.elasticsearch.EmployeeESDao;
import com.example.exampleappmicroservice.Documents.EmployeeDocument;
import com.example.exampleappmicroservice.Models.Employee;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl {

    @Autowired
    EmployeeDao employeeDao;

//    @Autowired
//    private FullTextSession fullTextSession;

//    public List<Employee> findByContent(String searchTerm) {
//        // Create a full-text query builder
//        QueryBuilder queryBuilder = fullTextSession.getSearchFactory().buildQueryBuilder(Employee.class);
//
//        // Build the search query using keyword(s) and indexed fields
//        FullTextQuery query = queryBuilder
//                .keywordFields("firstName", "lastName", "jobTitle") // Adjust fields as needed
//                .matchPhrase(searchTerm) // Adjust search strategy (e.g., .wildcard(), .fuzzy())
//                .createQuery();
//
//        // Execute the search and return results
//        return fullTextSession.createQuery(query).getResultList();
//    }

    public List<Employee> getAll() {
        return employeeDao.findAll();
    }



    @Autowired
    private EmployeeESDao employeeESDao;
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;
//    public List<EmployeeDocument> findByFirstName(String firstName) {
//        List<Employee> employees = employeeDao.findByFirstName(firstName);
//
//        // Convert and index each one in Elasticsearch
//        List<EmployeeDocument> documents = employees.stream()
//                .map(this::convertToDocument)
//                .peek(employeeESDao::save)
//                .collect(Collectors.toList());
//
//        return documents;
//    }

//    private EmployeeDocument convertToDocument(Employee employee) {
//        EmployeeDocument doc = new EmployeeDocument();
//        doc.setFirstName(employee.getFirstName());
//        doc.setLastName(employee.getLastName());
//        doc.setAge(employee.getAge());
//        doc.setAnneeTravail(employee.getAnneeTravail());
//        doc.setSalary(employee.getSalary());
//        doc.setId(String.valueOf(employee.getId())); // optional
//        return doc;
//    }

    public List<EmployeeDocument> searchByLastName(String name) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("lastName.keyword", name))
                .build();
        return elasticsearchRestTemplate.search(searchQuery, EmployeeDocument.class)
                .stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }
    public List<EmployeeDocument> searchByAgeRange(int min, int max) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.rangeQuery("age").gte(min).lte(max))
                .build();

        return elasticsearchRestTemplate.search(searchQuery, EmployeeDocument.class)
                .stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }

    public List<EmployeeDocument> searchByLastNameAndAge(String firstName, int age) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.boolQuery()
                        .must(QueryBuilders.matchQuery("name", firstName))
                        .filter(QueryBuilders.rangeQuery("age").gte(age)))

                .build();
        return elasticsearchRestTemplate.search(searchQuery, EmployeeDocument.class)
                .stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }
    public List<EmployeeDocument> searchByFirstNameBeginWith( String sequence) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.wildcardQuery("firstName.keyword", sequence+"*"))
                .build();

        return elasticsearchRestTemplate.search(searchQuery, EmployeeDocument.class)
                .stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }
    public List<EmployeeDocument> searchByFirstNameLike( String sequence) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.fuzzyQuery("firstName.keyword", sequence))
                .build();

        return elasticsearchRestTemplate.search(searchQuery, EmployeeDocument.class)
                .stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }
    public List<EmployeeDocument> searchByMulti( String sequence) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery(sequence, "firstName", "lastName"))
                .build();

        return elasticsearchRestTemplate.search(searchQuery, EmployeeDocument.class)
                .stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }
    public List<EmployeeDocument> searchByFirstNameBeginWithIgnoreCase( String sequence) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.wildcardQuery("firstName", sequence+"*"))
                .build();

        return elasticsearchRestTemplate.search(searchQuery, EmployeeDocument.class)
                .stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }

//    public List<EmployeeDocument> searchByFirstNameBeginWithIgnoreCase( String sequence) {
//        QueryBuilder fuzzy = QueryBuilders.fuzzyQuery("firstName", "atef")
//                .fuzziness(Fuzziness.AUTO);
//
//        QueryBuilder wildcard = QueryBuilders.wildcardQuery("firstName.keyword", "at*");
//
//        QueryBuilder combinedQuery = QueryBuilders.boolQuery()
//                .should(fuzzy)
//                .should(wildcard)
//                .minimumShouldMatch(1); // at least one must match
//
//    }

//    public List<EmployeeDocument> searchByKeyword(String name) {
//        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
//                .withQuery(QueryBuilders.matchQuery("lastName", name))
//                .build();
//        return elasticsearchRestTemplate.search(searchQuery, EmployeeDocument.class)
//                .stream()
//                .map(SearchHit::getContent)
//                .collect(Collectors.toList());
//    }
@Autowired
private RestHighLevelClient client;
    public List<String> suggestFirstNames(String userInput) throws IOException {
        SearchRequest searchRequest = new SearchRequest("employees");

        CompletionSuggestionBuilder suggestionBuilder = SuggestBuilders
                .completionSuggestion("firstNameSuggest")
                .prefix(userInput)
                .skipDuplicates(true)
                .size(5);

        SuggestBuilder suggestBuilder = new SuggestBuilder()
                .addSuggestion("name-suggest", suggestionBuilder);

        searchRequest.source().suggest(suggestBuilder);

        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);

        Suggest.Suggestion<? extends Suggest.Suggestion.Entry<? extends Suggest.Suggestion.Entry.Option>> suggestion =
                response.getSuggest().getSuggestion("name-suggest");

        List<String> results = new ArrayList<>();
        for (Suggest.Suggestion.Entry<? extends Suggest.Suggestion.Entry.Option> entry : suggestion.getEntries()) {
            for (Suggest.Suggestion.Entry.Option option : entry.getOptions()) {
                results.add(option.getText().string());
            }
        }

        return results;
    }
}

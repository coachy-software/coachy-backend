package life.coachy.backend.user;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

interface UserElasticRepository extends ElasticsearchRepository<User, String> {

}

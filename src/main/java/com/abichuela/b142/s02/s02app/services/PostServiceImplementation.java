package com.abichuela.b142.s02.s02app.services;

import com.abichuela.b142.s02.s02app.models.Post;
import com.abichuela.b142.s02.s02app.models.User;
import com.abichuela.b142.s02.s02app.repositories.PostRepository;
import com.abichuela.b142.s02.s02app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImplementation implements PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    public void createPost(Post newPost, Long userId) {
        User author = userRepository.findById(userId).get();
        Post post = new Post();
        post.setTitle(newPost.getTitle());
        post.setContent(newPost.getContent());
        post.setUser(author);
        postRepository.save(post);
    }

    public void updatePost(Long id, Post updatedPost) {
        Post existingPost = postRepository.findById(id).get();
        existingPost.setTitle(updatedPost.getTitle());
        existingPost.setContent(updatedPost.getContent());
        postRepository.save(existingPost);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    public Iterable<Post> getPosts(Long userId) {
        return postRepository.findAll();
    }
}
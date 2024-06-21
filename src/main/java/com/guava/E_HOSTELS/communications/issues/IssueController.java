package com.guava.E_HOSTELS.communications.issues;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class IssueController {

    private final IssueService issueService;
    private final IssueRepository issueRepository;

    @GetMapping("/fetch/issues/{userId}/{role}")
    public List<Issue> getIssuesByUser(@PathVariable Long userId, @PathVariable UserRole role) {
        return issueService.getIssuesByUser(userId, role);
    }

    @PostMapping("/create/issue")
    public Issue createIssue(@RequestBody Issue issue) {
        return issueService.createIssue(issue);
    }

    @DeleteMapping("/remove/issue/{id}")
    public void deleteIssue(@PathVariable Long id) {
        issueService.deleteIssue(id);
    }
}


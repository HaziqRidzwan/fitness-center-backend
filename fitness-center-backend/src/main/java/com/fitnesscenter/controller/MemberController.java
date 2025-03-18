package com.fitnesscenter.controller;

import com.fitnesscenter.model.Member;
import com.fitnesscenter.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Tells Spring this class handles web requests for the "/api/members" URL path
@RestController
@RequestMapping("/members")
public class MemberController {

    // Connect to the Member Service so we can manage member data
    @Autowired
    private MemberService memberService;

    // This method returns a list of all members (GET request to /api/members)
    @GetMapping
    public ResponseEntity<List<Member>> getAllMembers() {
        // Ask the service for all members (like pulling all member files)
        List<Member> members = memberService.getAllMembers();
        // Return the list to the user
        return ResponseEntity.ok(members);
    }

    // This method returns only active members
    @GetMapping("/active")
    public ResponseEntity<List<Member>> getActiveMembers() {
        // Ask the service for active members only (like filtering out expired memberships)
        List<Member> activeMembers = memberService.getActiveMembers();
        return ResponseEntity.ok(activeMembers);
    }

    // This method finds a specific member by their ID (GET request to /api/members/123)
    @GetMapping("/{id}")
    public ResponseEntity<?> getMemberById(@PathVariable Long id) {
        // Try to find the specific member file
        return memberService.getMemberById(id)
                .map(member -> ResponseEntity.ok(member)) // If found, return it
                .orElse(ResponseEntity.notFound().build()); // If not found, return "not found"
    }

    // This method searches for members by last name
    @GetMapping("/search")
    public ResponseEntity<List<Member>> searchMembers(@RequestParam String lastName) {
        // Search for members with matching last name (like using the filing system)
        List<Member> members = memberService.searchMembersByLastName(lastName);
        return ResponseEntity.ok(members);
    }

    // This method adds a new member (POST request to /api/members with member details)
    @PostMapping
    public ResponseEntity<Member> createMember(@RequestBody Member member) {
        // Save the new member form and get back the completed record
        Member savedMember = memberService.saveMember(member);
        // Return the new member with a "created" status (like giving a receipt)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMember);
    }

    // This method updates an existing member (PUT request to /api/members/123 with updated details)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateMember(@PathVariable Long id, @RequestBody Member member) {
        // Check if the member exists
        if (!memberService.getMemberById(id).isPresent()) {
            return ResponseEntity.notFound().build(); // If no such member, return "not found"
        }

        // Make sure the ID in the URL matches the member we're updating
        member.setId(id);

        // Save the updated member information
        Member updatedMember = memberService.saveMember(member);

        // Return the updated record
        return ResponseEntity.ok(updatedMember);
    }

    // This method marks a member as inactive instead of deleting them
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<?> deactivateMember(@PathVariable Long id) {
        // Try to deactivate the member
        boolean success = memberService.deactivateMember(id);

        if (success) {
            return ResponseEntity.ok("Member deactivated successfully");
        } else {
            return ResponseEntity.notFound().build(); // If no such member, return "not found"
        }
    }

    // This method completely deletes a member (DELETE request to /api/members/123)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMember(@PathVariable Long id) {
        // Check if the member exists
        if (!memberService.getMemberById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        // Delete the member record
        memberService.deleteMember(id);

        // Return success with no content (like a receipt that says "shredded")
        return ResponseEntity.noContent().build();
    }
}
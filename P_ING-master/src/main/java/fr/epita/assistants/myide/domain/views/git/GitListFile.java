package fr.epita.assistants.myide.domain.views.git;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.RemoteListCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GitListFile {
    private static AbstractTreeIterator prepareTreeParser(Repository repository, String objectId) throws IOException {
        // from the commit we can build the tree which allows us to construct the TreeParser
        //noinspection Duplicates
        try (RevWalk walk = new RevWalk(repository)) {
            RevCommit commit = walk.parseCommit(repository.resolve(objectId));
            RevTree tree = walk.parseTree(commit.getTree().getId());

            CanonicalTreeParser treeParser = new CanonicalTreeParser();
            try (ObjectReader reader = repository.newObjectReader()) {
                treeParser.reset(reader, tree.getId());
            }

            walk.dispose();

            return treeParser;
        }
    }

    public List<List<String>> listDiff(Repository repository, Git git, String oldCommit) throws GitAPIException, IOException {
        List<List<String>> paths = new ArrayList<List<String>>();
        List<String> defaultChanges = new ArrayList<String>();
        List<String> unversionedFiles = new ArrayList<String>();
        final List<DiffEntry> diffs = git.diff()
                .setOldTree(prepareTreeParser(repository, oldCommit))
                .call();
        System.out.println("Found: " + diffs.size() + " differences");
        for (DiffEntry diff : diffs) {
            System.out.println("Diff: " + diff.getChangeType() + ": " +
                    (diff.getOldPath().equals(diff.getNewPath()) ? diff.getNewPath() : diff.getOldPath() + " -> " + diff.getNewPath()));
            if (diff.getChangeType().equals(DiffEntry.ChangeType.ADD))
                unversionedFiles.add(diff.getNewPath());
            if (diff.getChangeType().equals(DiffEntry.ChangeType.MODIFY))
                defaultChanges.add(diff.getNewPath());
        }
        paths.add(defaultChanges);
        paths.add(unversionedFiles);
        return paths;
    }

    public List<String> getUnpushedCommits(Repository repository, Git git) throws GitAPIException, IOException {
        List<String> unpushedCommits = new ArrayList<String>();
        ObjectId head = repository.resolve(Constants.HEAD);
        ObjectId origin = repository.resolve("origin/" + repository.getBranch());
        Iterable<RevCommit> commits = git.log().addRange(origin, head).call();
        for (RevCommit commit : commits) {
            unpushedCommits.add(commit.getFullMessage());
        }
        return unpushedCommits;
    }

    public List<String> listRemotes(Git git) throws GitAPIException {
        List<String> remoteList = new ArrayList<String>();

        RemoteListCommand lsRemote = git.remoteList();

        var remotes = lsRemote.call();
        for (var remoteConfig : remotes) {
            remoteList.add(remoteConfig.getName());
        }

        return remoteList;
    }
}

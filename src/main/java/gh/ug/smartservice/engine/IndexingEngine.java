package gh.ug.smartservice.engine;

import gh.ug.smartservice.datastructures.hash.CustomMap;
import gh.ug.smartservice.datastructures.list.DynamicArrayList;
import gh.ug.smartservice.datastructures.tree.BTree;
import gh.ug.smartservice.datastructures.tree.BinarySearchTree;
import gh.ug.smartservice.datastructures.tree.RedBlackTree;
import gh.ug.smartservice.db.BookRequestDAO;
import gh.ug.smartservice.db.ShelfDAO;
import gh.ug.smartservice.model.BookRequest;
import gh.ug.smartservice.model.Shelf;

import java.util.ArrayList;
import java.util.List;

public class IndexingEngine {

    private static final int B_TREE_MIN_DEGREE = 3;

    private final BinarySearchTree<String> categoryIndex = new BinarySearchTree<>();
    private final RedBlackTree<String> sectionIndex = new RedBlackTree<>();
    private final BTree<String> statusIndex = new BTree<>(B_TREE_MIN_DEGREE);

    private final CustomMap<String, DynamicArrayList<Shelf>> shelvesByCategory = new CustomMap<>();
    private final CustomMap<String, DynamicArrayList<Shelf>> shelvesBySection = new CustomMap<>();
    private final CustomMap<String, DynamicArrayList<BookRequest>> requestsByStatus = new CustomMap<>();

    public IndexingEngine(ShelfDAO shelfDAO, BookRequestDAO bookRequestDAO) {
        for (Shelf shelf : shelfDAO.findAll()) {
            categoryIndex.insert(shelf.getCategory());
            sectionIndex.insert(shelf.getSection());
            group(shelvesByCategory, shelf.getCategory(), shelf);
            group(shelvesBySection, shelf.getSection(), shelf);
        }
        for (BookRequest request : bookRequestDAO.findAll()) {
            if (!statusIndex.search(request.getStatus())) {
                statusIndex.insert(request.getStatus());
            }
            group(requestsByStatus, request.getStatus(), request);
        }
    }

    public List<String> sortedCategories() {
        return categoryIndex.inorder();
    }

    public List<String> sortedSections() {
        return sectionIndex.inorder();
    }

    public List<String> sortedStatuses() {
        return statusIndex.traverse();
    }

    public boolean hasCategory(String category) {
        return categoryIndex.contains(category);
    }

    public boolean hasSection(String section) {
        return sectionIndex.contains(section);
    }

    public boolean hasStatus(String status) {
        return statusIndex.search(status);
    }

    public List<Shelf> findShelvesByCategory(String category) {
        return copyOrEmpty(shelvesByCategory.get(category));
    }

    public List<Shelf> findShelvesBySection(String section) {
        return copyOrEmpty(shelvesBySection.get(section));
    }

    public List<BookRequest> findRequestsByStatus(String status) {
        return copyOrEmpty(requestsByStatus.get(status));
    }

    private static <T> void group(CustomMap<String, DynamicArrayList<T>> map, String key, T value) {
        DynamicArrayList<T> bucket = map.get(key);
        if (bucket == null) {
            bucket = new DynamicArrayList<>();
            map.put(key, bucket);
        }
        bucket.add(value);
    }

    private static <T> List<T> copyOrEmpty(DynamicArrayList<T> bucket) {
        List<T> result = new ArrayList<>();
        if (bucket != null) {
            for (T item : bucket) {
                result.add(item);
            }
        }
        return result;
    }
}

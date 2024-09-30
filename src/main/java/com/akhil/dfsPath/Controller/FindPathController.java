package com.akhil.dfsPath.Controller;

import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/find-path")
@CrossOrigin(origins = "http://localhost:3000")
public class FindPathController {
    @PostMapping
    public Map<String, List<int[]>> findPath(@RequestBody Map<String, Object> points) {
        List<Integer> startList = (List<Integer>) points.get("start");
        List<Integer> endList = (List<Integer>) points.get("end");
        String algorithm = (String) points.get("algorithm");

        int[] start = {startList.get(0), startList.get(1)};
        int[] end = {endList.get(0), endList.get(1)};

        List<int[]> path = new ArrayList<>();
        boolean[][] visited = new boolean[20][20];

        if ("bfs".equalsIgnoreCase(algorithm)) {
            if (breadthFirstSearch(start[0], start[1], end, visited, path)) {
                return Collections.singletonMap("path", path);
            }
        } else {
            if (depthFirstSearch(start[0], start[1], end, visited, path)) {
                return Collections.singletonMap("path", path);
            }
        }
        return Collections.singletonMap("path", Collections.emptyList());
    }

    //recursive approach

//    private boolean depthFirstSearch(int x, int y, int[] end, boolean[][] visited, List<int[]> path) {
//        if (x < 0 || x >= 20 || y < 0 || y >= 20 || visited[x][y]) {
//            return false;
//        }
//
//        visited[x][y] = true;
//        path.add(new int[]{x, y});
//
//        if (x == end[0] && y == end[1]) {
//            return true;
//        }
//
//        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
//        for (int[] dir : directions) {
//            if (depthFirstSearch(x + dir[0], y + dir[1], end, visited, path)) {
//                return true;
//            }
//        }
//
//        path.remove(path.size() - 1);
//        return false;
//    }

    //iterative approach

    private boolean depthFirstSearch(int startX, int startY, int[] end, boolean[][] visited, List<int[]> path) {

        Stack<int[]> stack = new Stack<>();
        stack.push(new int[]{startX, startY});

        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        while (!stack.isEmpty()) {
            int[] current = stack.pop();
            int x = current[0];
            int y = current[1];

            if (visited[x][y]) {
                continue;
            }

            visited[x][y] = true;

            path.add(new int[]{x, y});
            if (x == end[0] && y == end[1]) {
                return true;
            }

            for (int[] dir : directions) {
                int newX = x + dir[0];
                int newY = y + dir[1];

                if (newX >= 0 && newX < 20 && newY >= 0 && newY < 20 && !visited[newX][newY]) {
                    stack.push(new int[]{newX, newY});
                }
            }

        }

        return false;
    }





    private boolean breadthFirstSearch(int startX, int startY, int[] end, boolean[][] visited, List<int[]> path) {

        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};


        Queue<List<int[]>> queue = new LinkedList<>();
        List<int[]> initialPath = new ArrayList<>();
        initialPath.add(new int[]{startX, startY});
        queue.add(initialPath);

        visited[startX][startY] = true;

        while (!queue.isEmpty()) {
            List<int[]> currentPath = queue.poll();
            int[] currentPos = currentPath.get(currentPath.size() - 1); // Get the last position in the path


            if (currentPos[0] == end[0] && currentPos[1] == end[1]) {
                path.clear();
                path.addAll(currentPath);
                return true;
            }


            for (int[] dir : directions) {
                int newX = currentPos[0] + dir[0];
                int newY = currentPos[1] + dir[1];


                if (newX >= 0 && newX < 20 && newY >= 0 && newY < 20 && !visited[newX][newY]) {
                    visited[newX][newY] = true;

                    List<int[]> newPath = new ArrayList<>(currentPath);
                    newPath.add(new int[]{newX, newY});
                    queue.add(newPath);
                }
            }
        }

        return false;
    }




}

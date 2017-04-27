package tags;

/**
 * <p>Class generate page counts.</p>
 *
 * 21.03.2017 by K.N.K
 */
public class PaginationFunction {
    static public String paggination(int currentPageNumber, int totalItems, int itemsPerPage, String postfix){
        StringBuilder res = new StringBuilder();

        if(totalItems <= itemsPerPage)
            return res.toString();

        int totalPages = (totalItems/itemsPerPage);
        if(totalItems % itemsPerPage != 0){
            totalPages++;
        }

        if(currentPageNumber > totalPages || currentPageNumber<=0)
            currentPageNumber = 1;

        if(totalItems <= currentPageNumber*itemsPerPage)
            currentPageNumber = totalPages;

        int start = currentPageNumber-1;
        if(start<=0)
            start=1;
        int end = currentPageNumber+1;
        if(end>=totalPages)
            end = totalPages;

        if(start>1)
            res.append("<a href='/search?page=1")
                    .append(postfix)
                    .append("'>1</a>, ");

        if(start>2)
            res.append("...,");

            for(int i=start; i<=end; i++){
                if(i == currentPageNumber){
                    res.append("<span style='font-weight: bold; color: red;'>")
                            .append(i)
                            .append("</span> ");
                } else {
                    res.append("<a href='/search?page=")
                            .append(i)
                            .append(postfix)
                            .append("'>")
                            .append(i)
                            .append("</a> ");
                }
                if(i<totalPages)
                    res.append(", ");
            }
            if(end+1<totalPages)
                res.append("...,");
            if(end< totalPages){
                if(totalPages == currentPageNumber){
                    res.append("<strong> ")
                            .append(totalPages)
                            .append(" </strong>");
                } else {
                    res.append(" <a href='/search?page=")
                            .append(totalPages)
                            .append(postfix)
                            .append("'>")
                            .append(totalPages)
                            .append("</a>");
                }
            }

        return res.toString();
    }
}

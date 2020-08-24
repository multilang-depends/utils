/*
MIT License

Copyright (c) 2018-2019 Gang ZHANG

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package multilang.depends.util.file.strip;

import multilang.depends.util.file.FileUtil;

import java.io.File;

public class LeadingNameStripper implements ILeadingNameStrippper {
    String leadingSrcPath;
    private String[] additionalStripPaths;
    private boolean isStripLeadingPath;

    public LeadingNameStripper(boolean isStripLeadingPath, String leadingSrcPath, String[] additionalStripPaths) {
        this.isStripLeadingPath = isStripLeadingPath;
        this.leadingSrcPath = leadingSrcPath;
        this.additionalStripPaths = additionalStripPaths;
    }

    @Override
    public String stripFilename(String path) {
        try {
            for (String p : additionalStripPaths) {
                String prefix = p;
                if (isStripLeadingPath)
                    p = leadingSrcPath + File.separator + p;
                prefix = FileUtil.uniqFilePath(prefix);
                if (path.startsWith(prefix)) {
                    return path.substring(prefix.length() + 1);
                }
            }

            if (path.startsWith(leadingSrcPath) && isStripLeadingPath) {
                path = path.substring(leadingSrcPath.length() + 1);
            }
            return path;
        } catch (Exception e) {
            return path;
        }
    }
}

package zirc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class INI {

	/** Map containing all categories from the ini file. */
	private HashMapINI<String, HashMapINI<String, Object>> mapSections;

	/**
	 * Map containing all categories from the ini file, storing strings for
	 * variables.
	 */
	private Map<String, HashMapINI<String, String>> mapSectionsAsStrings;

	private Map<String, List<String>> mapSectionComments;

	private Map<String, HashMapINI<String, List<String>>> mapSectionVariableComments;

	private List<String> listSections;

	private File file;

	public INI(File file) {
		setFile(file);
		reset();
	}

	public void reset() {
		if (mapSections == null) {
			mapSections = new HashMapINI<String, HashMapINI<String, Object>>("");
		} else {
			mapSections.clear();
		}

		if (listSections == null) {
			listSections = new ArrayList<String>();
		} else {
			listSections.clear();
		}

		if (mapSectionsAsStrings == null) {
			mapSectionsAsStrings = new HashMap<String, HashMapINI<String, String>>();
		} else {
			mapSectionsAsStrings.clear();
		}

		if (mapSectionVariableComments == null) {
			mapSectionVariableComments = new HashMap<String, HashMapINI<String, List<String>>>();
		} else {
			mapSectionVariableComments.clear();
		}

		if (mapSectionComments == null) {
			mapSectionComments = new HashMap<String, List<String>>();
		} else {
			mapSectionComments.clear();
		}

	}

	public void read() throws IOException {
		Scanner scanner = new Scanner(file);

		HashMapINI<String, Object> mapSection = null;
		HashMapINI<String, String> mapSectionAsString = null;

		List<String> comment = null;

		HashMapINI<String, List<String>> mapVariableComments = null;

		String mapSectionName = null;
		String[] variableSplit = null;

		boolean numericalSuccess = false;
		boolean lastLineComment = false;

		mapSectionAsString = new HashMapINI<String, String>(
				mapSectionName);
		mapSection = new HashMapINI<String, Object>(mapSectionName);
		mapSections.put("", mapSection);
		mapVariableComments = new HashMapINI<String, List<String>>(
				mapSectionName);
		mapSectionVariableComments.put("", mapVariableComments);
		
		
		String newLine = null;
		while (scanner.hasNextLine()) {

			newLine = scanner.nextLine().trim();

			if (newLine.isEmpty()) {
				continue;
			}

			// If the line is a comment, continue to the next line.
			if (newLine.startsWith(";")) {
				if (!lastLineComment) {
					comment = new ArrayList<String>();
				}
				String nextComment = newLine.split(";")[1].trim();
//				System.out.println("Comment Read: "
//						+ newLine.split(";")[1].trim());
				comment.add(nextComment);
				if (mapSectionName != null) {
					mapSectionVariableComments.put(mapSectionName,
							mapVariableComments);
				}
				lastLineComment = true;
				continue;
			}

			// If the line is the beginning of a new section.
			if (newLine.startsWith("[") && newLine.endsWith("]")) {

				if (mapVariableComments != null) {
					mapSectionVariableComments.put(mapSectionName,
							mapVariableComments);
				}

				mapVariableComments = new HashMapINI<String, List<String>>(
						mapSectionName);

				// Place the current section into the main Map.
				if (mapSection != null) {
					mapSections.put(mapSectionName, mapSection);
					mapSectionsAsStrings
							.put(mapSectionName, mapSectionAsString);

				}

				mapSectionName = newLine.substring(1, newLine.length() - 1);
				mapSection = mapSections.get(mapSectionName);

				if (mapSection != null) {
					mapSectionAsString = mapSectionsAsStrings
							.get(mapSectionName);
					if (mapSectionAsString == null) {
						mapSectionAsString = new HashMapINI<String, String>(
								mapSectionName);
					}
				} else {
//					System.out.println("Section is null: " + mapSectionName);
					mapSectionAsString = new HashMapINI<String, String>(
							mapSectionName);
					mapSection = new HashMapINI<String, Object>(mapSectionName);
					if (lastLineComment) {
						mapSectionComments.put(mapSectionName, comment);
					}
					mapSections.put(mapSectionName, mapSection);
				}
				lastLineComment = false;
				continue;
			}

			// This is a variable.
			if (newLine.contains("=")) {
				variableSplit = newLine.split("=");
				
				// If the syntax is correct.
				if (variableSplit.length == 2) {
					// If the sides of the '=' character are valid.
					if (variableSplit[0] != null && variableSplit[0].length() > 0) {
//						if (variableSplit[1].isEmpty()) {
//							String emptyString = "";
//							mapSection.put(variableSplit[0], emptyString);
//							lastLineComment = false;
//							continue;
//						}
//						if (variableSplit[1].contains("."))
//							try {
//								Double d = Double.parseDouble(variableSplit[1]);
//								mapSection.put(variableSplit[0], d);
//								numericalSuccess = true;
//								lastLineComment = false;
//								continue;
//							} catch (NumberFormatException e) {
//							}
//
//						else {
//							// Try Boolean.
//							if (isBoolean(variableSplit[1])) {
//								try {
//									Boolean b = Boolean
//											.parseBoolean(variableSplit[1]);
//									mapSection.put(variableSplit[0], b);
//									numericalSuccess = true;
//								} catch (NumberFormatException e) {
//
//								}
//							}
//							// Try Byte.
//							if (!numericalSuccess) {
//								try {
//									Byte b = Byte.parseByte(variableSplit[1]);
//									mapSection.put(variableSplit[0], b);
//									numericalSuccess = true;
//								} catch (NumberFormatException e) {
//
//								}
//							}
//							// Try Short.
//							if (!numericalSuccess) {
//								try {
//									Short s = Short
//											.parseShort(variableSplit[1]);
//									mapSection.put(variableSplit[0], s);
//									numericalSuccess = true;
//								} catch (NumberFormatException e) {
//
//								}
//							}
//
//							// Try Integer.
//							if (!numericalSuccess) {
//								try {
//									Integer i = Integer
//											.parseInt(variableSplit[1]);
//									mapSection.put(variableSplit[0], i);
//									numericalSuccess = true;
//								} catch (NumberFormatException e) {
//
//								}
//							}
//
//							// Try Long.
//							if (!numericalSuccess) {
//								try {
//									Long l = Long.parseLong(variableSplit[1]);
//									mapSection.put(variableSplit[0], l);
//									numericalSuccess = true;
//								} catch (NumberFormatException e) {
//
//								}
//							}
//						}
						// Store object as String for convenience.
						mapSectionAsString.put(variableSplit[0],
								variableSplit[1]);

						// At this point, we can only handle the variable as a
						// String.
						if (!numericalSuccess) {
							mapSection.put(variableSplit[0], variableSplit[1]);
						}

						// Stores comment for writing and reference purposes.
						if (lastLineComment) {
							mapVariableComments.put(variableSplit[0], comment);
						} else {
							mapVariableComments.remove(variableSplit[0]);
						}
						lastLineComment = false;
					}
				} else if (!variableSplit[0].isEmpty()) {
					mapSection.put(variableSplit[0], null);

					// Stores comment for writing and reference purposes.
					if (lastLineComment) {
						mapVariableComments.put(variableSplit[0], comment);
					} else {
						mapVariableComments.remove(variableSplit[0]);
					}
					lastLineComment = false;
				} else {
//					System.out.println("Ignoring invalid configuration: "
//							+ newLine);
					lastLineComment = false;
				}
			}
		}

		if (mapVariableComments != null) {
			mapSectionVariableComments.put(mapSectionName, mapVariableComments);
		}
		scanner.close();
	}

	public void save() throws IOException {
		saveFileAs(file);
	}

	public void saveFileAs(File file) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos,
				"UTF-8"));

		HashMapINI<String, List<String>> mapVComments = null;
		List<String> comments = null;
		String newLine = System.getProperty("line.separator");
		HashMapINI<String, Object> mapSection = null;
		boolean firstSection = true;
		// String sectionName = null;

		for (String sectionName : listSections) {

//			System.out.println("Section: " + sectionName);
			mapSection = mapSections.get(sectionName);

			if (firstSection) {
				firstSection = false;
			} else {
				writer.write(newLine);
			}

			comments = mapSectionComments.get(sectionName);

			if (comments != null) {
				for (String comment : comments) {
					if(comment != null) {						
						if (!comment.startsWith("#") || comment.startsWith("//")) {
							comment = "# " + comment + newLine;
						}
						writer.write(comment);
					}
				}
			}

			writer.write("[" + sectionName + "]" + newLine);

			mapVComments = mapSectionVariableComments.get(sectionName);

			for (String variable : mapSection.getKeysOrdered()) {
//				System.out.println("\t Variable: " + variable);

				if (mapVComments != null) {
					comments = mapVComments.get(variable);
				}

//				System.out.println("\t\t Comments: " + comments);
				if (comments != null) {
					String newComment;
					for (String comment : comments) {
						if (comment != null) {
							if (comment.startsWith(";")) {
								newComment = comment;
							} else {
								newComment = "; " + comment;
							}
							writer.write(newComment + newLine);
						}
					}
				}

				Object o = mapSection.get(variable);
				if (o == null) {
					writer.write(variable + "=" + newLine);
				} else if (o instanceof String) {
					writer.write(variable + "=" + (String) o + newLine);
				} else if (o instanceof Boolean) {
					writer.write(variable + "=" + ((Boolean) o).toString()
							+ newLine);
				} else if (o instanceof Integer) {
					writer.write(variable + "=" + ((Integer) o).intValue()
							+ newLine);
				} else {
					writer.write(variable + "=" + o.toString() + newLine);
				}
				writer.write(newLine);
			}
		}
		writer.close();
	}

//	private boolean isBoolean(String string) {
//		String s = string.toLowerCase();
//		return s.equals("true") || s.equals("false") || s.equals("0")
//				|| s.equals("1");
//	}

	public void setComments(String sectionName, String variable,
			List<String> comments) {
		HashMapINI<String, List<String>> mapComments = getSectionComments(sectionName);

		if (mapComments == null) {
			mapComments = new HashMapINI<String, List<String>>(sectionName);
			mapSectionVariableComments.put(sectionName, mapComments);
		}

		mapComments.put(variable, comments);
	}

	public void setComments(String sectionName, String variable,
			String... comments) {

		List<String> listComments = new ArrayList<String>();

		for (String comment : comments) {
			listComments.add(comment);
		}

		setComments(sectionName, variable, listComments);
	}

	public void setVariableComment(String sectionName, String variable,
			String comment) {
		List<String> listComments = new ArrayList<String>();
		listComments.add(comment);
		setComments(sectionName, variable, listComments);
	}

	public void appendVariableComment(String sectionName, String variable,
			String comment) {
		HashMapINI<String, List<String>> mapComments = getSectionComments(sectionName);

		if (mapComments == null) {
			mapComments = new HashMapINI<String, List<String>>(sectionName);
			mapSectionVariableComments.put(sectionName, mapComments);
		}

		List<String> listComments = mapComments.get(variable);
		if (listComments == null) {
			listComments = new ArrayList<String>();
			mapComments.put(variable, listComments);
		}

		listComments.add(comment);
	}

	public void clearVariableComments(String sectionName, String variable) {
		Map<String, List<String>> mapComments = getSectionComments(sectionName);

		if (mapComments != null) {
			mapComments.remove(variable);
		}
	}

	private HashMapINI<String, List<String>> getSectionComments(
			String sectionName) {
		return mapSectionVariableComments.get(sectionName);
	}

	public void removeSectionComments(String sectionName) {

	}

	public boolean hasComment(String sectionName, String variable) {
		HashMapINI<String, List<String>> mapComments = getSectionComments(sectionName);

		if (mapComments != null) {
			List<String> comments = mapComments.get(variable);

			return comments != null && !comments.isEmpty();
		}
		return false;
	}

	private HashMapINI<String, List<String>> createVariableCommentsMap(
			String sectionName) {
		HashMapINI<String, List<String>> mapVComments = null;

		mapVComments = mapSectionVariableComments.get(sectionName);
		if (mapVComments != null) {
			return mapVComments;
		} else {
			mapVComments = new HashMapINI<String, List<String>>(sectionName);
			mapSectionVariableComments.put(sectionName, mapVComments);
			return mapVComments;
		}
	}

	public String getVariableAsString(String section, String variable) {
		Object o = mapSections.get(section).get(variable);
		if(o == null) return "";
		else return o.toString();
	}

	public Object getVariable(String section, String variable) {
		return mapSections.get(section).get(variable);
	}

	public void setVariable(String sectionName, String variableName,
			Object variable, String... comments) {

		List<String> listComments = null;

		if (comments.length > 0) {
			listComments = new ArrayList<String>();
			for (String comment : comments) {
				listComments.add(comment);
			}
		}

		setVariable(sectionName, variableName, variable, listComments);
	}

	public void setVariable(String sectionName, String variableName,
			Object variable, List<String> comments) {
		Map<String, Object> map = getSection(sectionName);
		if (map == null) {
			map = createSection(sectionName);
		}

		map.put(variableName, variable);
		if (comments != null) {
			HashMapINI<String, List<String>> mapVComments = mapSectionVariableComments
					.get(sectionName);

//			System.out.println("Putting comments: " + mapVComments + " -> "
//					+ variableName + " -> " + comments);

			mapVComments.put(variableName, comments);
		}
	}

	public HashMapINI<String, Object> createSection(String sectionName,
			String... comments) {
		List<String> listComments = null;

		listComments = new ArrayList<String>();

		if (comments.length > 0) {
			for (String comment : comments) {
				listComments.add(comment);
			}
		}
		return createSection(sectionName, listComments);
	}

	public HashMapINI<String, Object> createSection(String sectionName,
			List<String> comments) {
		HashMapINI<String, Object> map = null;

		map = mapSections.get(sectionName);

		if (map == null) {
//			System.out.println("Section created: " + sectionName);
			map = new HashMapINI<String, Object>(sectionName);
			mapSections.put(sectionName, map);
			listSections.add(sectionName);
		}

		createVariableCommentsMap(sectionName);
		mapSectionComments.put(sectionName, comments);
		if (mapSectionVariableComments.get(sectionName) == null) {
			mapSectionVariableComments.put(sectionName,
					new HashMapINI<String, List<String>>(sectionName));
		}
		return map;
	}

	public Map<String, Object> getSection(String section) {
		return mapSections.get(section);
	}

	public void setFile(File file) {
		this.file = file;
	}

	public class HashMapINI<K, V> extends HashMap<K, V> {
		private static final long serialVersionUID = 3570436213264485075L;
		private String name;

		private List<K> listOrderedKeys;

		public HashMapINI(String name) {
			super();
			setName(name);
			listOrderedKeys = new ArrayList<K>();
		}

		public String getName() {
			return this.name;
		}

		@Override
		public V put(K key, V value) {
			if (!listOrderedKeys.contains(key)) {
				listOrderedKeys.add(key);
			}

			return super.put(key, value);
		}

		@Override
		public void clear() {
			super.clear();
			listOrderedKeys.clear();
		}

		@Override
		public V remove(Object key) {
			if (listOrderedKeys.contains(key)) {
				listOrderedKeys.remove(key);
			}

			return super.remove(key);
		}

		public List<K> getKeysOrdered() {
			return listOrderedKeys;
		}

		public void setName(String name) {
			this.name = name;
		}

	}

	public File getFile() {
		return this.file;
	}
}
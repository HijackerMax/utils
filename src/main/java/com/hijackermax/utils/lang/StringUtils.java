package com.hijackermax.utils.lang;

import com.hijackermax.utils.builders.Transformer;
import com.hijackermax.utils.entities.Single;
import com.hijackermax.utils.enums.ComparisonOperators;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.hijackermax.utils.lang.CollectionUtils.safeStreamOf;
import static com.hijackermax.utils.lang.NumberUtils.requireGreaterThanZero;
import static com.hijackermax.utils.lang.ObjectUtils.valueOrDefault;
import static java.util.Objects.isNull;

/**
 * Set of utility methods that can help to work with Strings
 */
public final class StringUtils {
    /**
     * Empty {@link String} constant
     */
    public static final String EMPTY = "";
    /**
     * Blank {@link String} constant
     */
    public static final String BLANK = " ";
    /**
     * At {@link String} constant
     */
    public static final String AT = "@";
    /**
     * Semicolon {@link String} constant
     */
    public static final String SEMICOLON = ";";
    /**
     * Comma {@link String} constant
     */
    public static final String COMMA = ",";
    /**
     * Period {@link String} constant
     */
    public static final String PERIOD = ".";
    /**
     * Dash {@link String} constant
     */
    public static final String DASH = "-";
    /**
     * Underscore {@link String} constant
     */
    public static final String UNDERSCORE = "_";

    /**
     * Zero {@link String} constant
     */
    public static final String ZERO = "0";

    /**
     * One {@link String} constant
     */
    public static final String ONE = "1";

    private static final Pattern WHITESPACES_PATTERN = Pattern.compile("\\s+");
    private static final Pattern NON_DIGITS_PATTERN = Pattern.compile("[^\\d.]");
    private static final Pattern DIGITS_PATTERN = Pattern.compile("[^\\D.]");
    private static final Pattern NAMED_FORMAT_PATTERN = Pattern.compile("(?iu)(?:\\$\\{(?:(\\w+)(?:\\?`([^`]+[\\p{Alnum}\\p{IsAlnum}\\p{Punct}\\p{IsPunct}']+[\\s]?[^`]+)`)?(%\\w+)?)\\})");

    private StringUtils() {
    }

    /**
     * Checks if {@link String} is empty or null
     *
     * @param value {@link String} to check
     * @return true if {@link String} is empty or null, otherwise false
     * @since 0.0.1
     */
    public static boolean isEmpty(String value) {
        return isNull(value) || value.isEmpty();
    }

    /**
     * Checks if {@link String} is not empty or null
     *
     * @param value {@link String} to check
     * @return true if {@link String} is not empty or null, otherwise false
     * @since 0.0.1
     */
    public static boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }

    /**
     * Checks if {@link String} is blank, empty or null
     *
     * @param value {@link String} to check
     * @return true if {@link String} is blank, empty or null, otherwise false
     * @since 0.0.1
     */
    public static boolean isBlank(String value) {
        return isNull(value) || value.isBlank();
    }

    /**
     * Checks if {@link String} is not blank, empty or null
     *
     * @param value {@link String} to check
     * @return true if {@link String} is not blank, empty or null, otherwise false
     * @since 0.0.1
     */
    public static boolean isNotBlank(String value) {
        return !isBlank(value);
    }

    /**
     * Checks if {@link String} is not empty and ends with provided suffix
     *
     * @param source {@link String} to check
     * @param suffix {@link String} suffix
     * @return true if {@link String} is not empty and ends with provided suffix, otherwise false
     * @since 0.0.1
     */
    public static boolean safeEndsWith(String source, String suffix) {
        return StringUtils.isNotEmpty(source) && StringUtils.isNotEmpty(suffix) && source.endsWith(suffix);
    }

    /**
     * Checks if {@link String} is not empty and contains provided char sequence
     *
     * @param source {@link String} to check
     * @param value  {@link CharSequence} searchable char sequence
     * @return true if {@link String} is not empty and contains provided {@link CharSequence}, otherwise false
     * @since 0.0.2
     */
    public static boolean safeContains(String source, CharSequence value) {
        return StringUtils.isNotEmpty(source) && Objects.nonNull(value) && source.contains(value);
    }

    /**
     * Checks if left input {@link String} equals to right input {@link String} ignoring case
     *
     * @param left  first {@link String} to check
     * @param right second {@link String} to check
     * @return true if first input {@link String} equals to second input {@link String} ignoring case, otherwise false
     * @since 0.0.1
     */
    public static boolean equalsIgnoreCase(String left, String right) {
        return Objects.nonNull(left) ? left.equalsIgnoreCase(right) : isNull(right);
    }

    /**
     * Checks if left input {@link String} equals to right input {@link String}
     *
     * @param left  first {@link String} to check
     * @param right second {@link String} to check
     * @return true if first input {@link String} equals to second input {@link String}, otherwise false
     * @since 0.0.1
     */
    public static boolean equals(String left, String right) {
        return Objects.nonNull(left) ? left.equals(right) : isNull(right);
    }

    /**
     * Trims string if non-null {@link String} is provided, otherwise returns null
     *
     * @param source {@link String} to trim
     * @return trimmed {@link String} or null if input string is null
     * @since 0.0.1
     */
    public static String trim(String source) {
        return isNull(source) ? null : source.trim();
    }

    /**
     * Returns {@link Function} that accepts {@link Collection} and returns a {@link String}
     * built from non-null collection values, separated by provided delimiter
     *
     * @param delimiter         {@link String} which should separate values in resulting {@link String}
     * @param toStringConverter {@link Function} which converts {@link Collection} to {@link String}
     * @param <T>               {@link Collection} elements type
     * @return {@link String} built from on-null collection values,
     * separated by provided delimiter, or empty {@link String} if collection is empty or null
     * @since 0.0.1
     */
    public static <T> Function<Collection<T>, String> join(String delimiter, Function<T, String> toStringConverter) {
        return collection -> join(collection, delimiter, toStringConverter);

    }

    /**
     * Returns a {@link String} built from non-null {@link Collection} values, separated by provided delimiter
     *
     * @param collection        {@link Collection} of elements that should be joined
     * @param delimiter         {@link String} which should separate values in resulting {@link String}
     * @param toStringConverter {@link Function} which converts {@link Collection} to {@link String}
     * @param <T>               {@link Collection} elements type
     * @return {@link String} built from on-null collection values,
     * separated by provided delimiter, or empty {@link String} if collection is empty or null
     * @since 0.0.1
     */
    public static <T> String join(Collection<? extends T> collection,
                                  String delimiter,
                                  Function<? super T, String> toStringConverter) {
        return safeStreamOf(collection)
                .filter(Objects::nonNull)
                .map(toStringConverter)
                .map(StringUtils::trimToEmpty)
                .filter(StringUtils::isNotEmpty)
                .collect(Collectors.joining(delimiter));
    }

    /**
     * Removes all non-numeric chars if non-null {@link String} is provided, otherwise returns empty string
     *
     * @param source {@link String} that should be processed
     * @return {@link String} with numeric chars only or empty string if input string is blank or null
     * @since 0.0.1
     */
    public static String removeNonDigits(String source) {
        return isBlank(source) ? EMPTY : NON_DIGITS_PATTERN.matcher(source).replaceAll(EMPTY);
    }

    /**
     * Trims string if non-null {@link String} is provided, otherwise returns empty string
     *
     * @param source {@link String} to trim
     * @return trimmed {@link String} or empty string if input string is null
     * @since 0.0.1
     */
    public static String trimToEmpty(String source) {
        return isEmpty(source) ? EMPTY : source.trim();
    }

    /**
     * Pads left provided source {@link String} with provided filler {@link String} provided amount of times
     * if source {@link String} is shorter than required
     *
     * @param source         {@link String} to be padded
     * @param requiredLength {@link Integer} that represents required length of resulting {@link String}
     * @param filler         {@link String} which can be used to extend source {@link String}
     * @return padded {@link String} with provided filler {@link String} provided amount of times
     * if source {@link String} is shorter than required,
     * if source {@link String} is longer or has same length as required returns source {@link String} without changes,
     * if source {@link String} or filler {@link String} is null returns null
     * @since 0.0.2
     */
    public static String padLeft(String source, int requiredLength, String filler) {
        return pad(source, requiredLength, filler, false);
    }

    /**
     * Pads right provided source {@link String} with provided filler {@link String} provided amount of times
     * if source {@link String} is shorter than required
     *
     * @param source         {@link String} to be padded
     * @param requiredLength {@link Integer} that represents required length of resulting {@link String}
     * @param filler         {@link String} which can be used to extend source {@link String}
     * @return padded {@link String} with provided filler {@link String} provided amount of times
     * if source {@link String} is shorter than required,
     * if source {@link String} is longer or has same length as required returns source {@link String} without changes,
     * if source {@link String} or filler {@link String} is null returns null
     * @since 0.0.2
     */
    public static String padRight(String source, int requiredLength, String filler) {
        return pad(source, requiredLength, filler, true);
    }

    private static String pad(String source, int requiredLength, String filler, boolean appendToEnd) {
        if (isNull(source) || isNull(filler)) {
            return null;
        }
        int sourceLength = source.length();
        int fillerLength = filler.length();
        int diff = requiredLength - sourceLength;
        if (diff <= 0) {
            return source;
        }
        char[] missingChars = new char[diff];
        char[] fillerChars = filler.toCharArray();
        for (int i = 0; i < diff; ++i) {
            missingChars[i] = fillerChars[i % fillerLength];
        }
        String missingStringPart = new String(missingChars);
        return appendToEnd ? source.concat(missingStringPart) : missingStringPart.concat(source);
    }

    /**
     * Compresses source {@link String} using GZIP and encodes it with Base64
     *
     * @param source {@link String} that needs to be compressed
     * @return compressed with GZIP and encoded with Base64 {@link String} or empty {@link String} if source is empty or null
     * @throws IOException in case of problems during source {@link String} compression
     * @since 0.0.3
     */
    public static String compress(String source) throws IOException {
        return compress(source, Base64.getEncoder()::encodeToString);
    }

    /**
     * Compresses source {@link String} using GZIP and encodes it with provided encoder
     *
     * @param source  {@link String} that needs to be compressed
     * @param encoder byte array to {@link String} encoder
     * @return compressed with GZIP and encoded with provided encoder {@link String} or empty {@link String} if source is empty or null
     * @throws IOException in case of problems during source {@link String} compression
     * @since 0.0.6
     */
    public static String compress(String source, Function<byte[], String> encoder) throws IOException {
        return ObjectUtils.compress(source.getBytes(StandardCharsets.UTF_8), encoder);
    }

    /**
     * Decodes source {@link String} from Base64 and decompresses it with GZIP
     *
     * @param source {@link String} that needs to be decompressed
     * @return decoded from Base64 and decompressed with GZIP {@link String} or empty {@link String} if source is empty or null
     * @throws IOException in case of problems during source {@link String} decompression
     * @since 0.0.3
     */
    public static String decompress(String source) throws IOException {
        return decompress(source, Base64.getDecoder()::decode);
    }

    /**
     * Decodes source {@link String} using provided decoder and decompresses it with GZIP
     *
     * @param source  {@link String} that needs to be decompressed
     * @param decoder {@link String} to byte array decoder
     * @return decoded with provided decoder and decompressed with GZIP {@link String} or empty {@link String} if source is empty or null
     * @throws IOException in case of problems during source {@link String} decompression
     * @since 0.0.6
     */
    public static String decompress(String source, Function<String, byte[]> decoder) throws IOException {
        return new String(ObjectUtils.decompress(source, decoder), StandardCharsets.UTF_8);
    }

    /**
     * Returns provided value {@link String} or default {@link String} if value is null, empty or blank
     *
     * @param value        that can be null, blank or empty
     * @param defaultValue fallback value
     * @return value if it is not null, blank or empty, otherwise defaultValue
     * @since 0.0.5
     */
    public static String notBlankOrElse(String value, String defaultValue) {
        return isBlank(value) ? defaultValue : value;
    }

    /**
     * Remove whitespaces if non-null {@link String} is provided, otherwise returns empty string
     *
     * @param source {@link String} to process
     * @return {@link String} with removed whitespaces or empty string if input string is null
     * @since 0.0.6
     */
    public static String removeWhitespaces(String source) {
        return isBlank(source) ? EMPTY : WHITESPACES_PATTERN.matcher(source).replaceAll(EMPTY);
    }

    /**
     * Returns provided value {@link String} or {@link String} provided by default
     * value {@link Supplier} if value is null, empty or blank
     *
     * @param value                that can be null, blank or empty
     * @param defaultValueSupplier fallback value {@link Supplier}
     * @return value if it is not null, blank or empty, otherwise default value {@link Supplier} invoke result
     * @since 0.0.8
     */
    public static String notBlankOrElseGet(String value, Supplier<String> defaultValueSupplier) {
        return isBlank(value) ? defaultValueSupplier.get() : value;
    }

    /**
     * Removes all digit chars if non-null {@link String} is provided, otherwise returns empty string
     *
     * @param source {@link String} that should be processed
     * @return {@link String} with non-numeric chars only or empty string if input string is blank or null
     * @since 0.0.8
     */
    public static String removeDigits(String source) {
        return isBlank(source) ? EMPTY : DIGITS_PATTERN.matcher(source).replaceAll(EMPTY);
    }

    /**
     * Replaces provided {@link CharSequence} values in provided {@link String} with corresponding replacement sequences
     *
     * @param source       {@link String} that should be processed
     * @param replacements {@link Map} which contains {@link CharSequence} that should be replaced as key,
     *                     and {@link CharSequence} that should be used as replacement as value
     * @return original string if replacement map is empty or null,
     * processed {@link String} or empty string if input string is blank or null
     * @since 0.0.8
     */
    public static String replace(String source, Map<CharSequence, CharSequence> replacements) {
        if (isEmpty(source)) {
            return EMPTY;
        }
        Single<String> result = new Single<>(source);
        CollectionUtils.safeForEach(
                replacements,
                (from, to) -> result.modifyValueIfSatisfies(s -> s.replace(from, to), v -> v.contains(from))
        );
        return result.getValue();
    }

    /**
     * Provides {@link UnaryOperator} which replaces provided {@link CharSequence} values in provided {@link String}
     * with corresponding replacement sequences
     *
     * @param replacements {@link Map} which contains {@link CharSequence} that should be replaced as key,
     *                     and {@link CharSequence} that should be used as replacement as value
     * @return {@link UnaryOperator} which replaces provided {@link CharSequence} values
     * in provided {@link String} with corresponding replacement sequences,
     * see {@link com.hijackermax.utils.lang.StringUtils#replace(String, Map)} for additional details
     * @since 0.0.8
     */
    public static UnaryOperator<String> replace(Map<CharSequence, CharSequence> replacements) {
        return source -> replace(source, replacements);
    }

    /**
     * Replaces provided substring value in source {@link String} with provided replacement {@link String}
     *
     * @param source {@link String} that should be processed
     * @param from   substring that should be replaced
     * @param to     replacement substring
     * @return original string if from or to substrings are empty or null,
     * processed {@link String} or empty string if input string is blank or null
     * @since 0.0.8
     */
    public static String replaceIgnoreCase(String source, String from, String to) {
        if (isEmpty(from) || isEmpty(to)) {
            return source;
        }
        return isBlank(source) ? EMPTY : source.replaceAll(String.format("(?i)%s", from), to);
    }

    /**
     * Provides {@link Predicate} which checks if evaluated string starts with provided prefix
     *
     * @param prefix the prefix
     * @return {@link Predicate} which checks if evaluated string starts with provided prefix,
     * if source or prefix are empty or null result will be false
     * @since 0.0.8
     */
    public static Predicate<String> startsWith(String prefix) {
        return source -> isNotEmpty(source) && isNotEmpty(prefix) && source.startsWith(prefix);
    }

    /**
     * Provides {@link Predicate} which checks if evaluated string ends with provided suffix
     *
     * @param suffix the suffix
     * @return {@link Predicate} which checks if evaluated string ends with provided suffix,
     * if source or suffix are empty or null result will be false
     * @since 0.0.8
     */
    public static Predicate<String> endsWith(String suffix) {
        return source -> isNotEmpty(source) && isNotEmpty(suffix) && source.endsWith(suffix);
    }

    /**
     * Provides {@link Predicate} which checks if evaluated string contains provided fragment
     *
     * @param fragment to search for
     * @return {@link Predicate} which checks if evaluated string contains provided fragment,
     * if source or fragment are empty or null result will be false
     * @since 0.0.8
     */
    public static Predicate<String> contains(String fragment) {
        return source -> isNotEmpty(source) && isNotEmpty(fragment) && source.contains(fragment);
    }

    /**
     * Provides {@link Predicate} which checks if evaluated string satisfies length comparison
     *
     * @param operator  the length comparison operator
     * @param reference thr reference value that should be compared to string length
     * @return {@link Predicate} which checks if evaluated string satisfies length comparison,
     * if source is empty or null result will be false
     * @throws IllegalArgumentException if reference value is equal to zero or negative
     * @since 0.0.8
     */
    public static Predicate<String> hasLength(ComparisonOperators operator, int reference) {
        return source -> isNotEmpty(source) && NumberUtils.compare(source.length(), requireGreaterThanZero(reference), operator);
    }

    /**
     * Passes provided {@link String} if not empty to the provided string {@link Consumer}
     *
     * @param value         the value to check
     * @param valueConsumer non-empty value consumer
     * @since 0.0.9
     */
    public static void ifNotEmpty(String value, Consumer<String> valueConsumer) {
        if (isNotEmpty(value)) {
            valueConsumer.accept(value);
        }
    }

    /**
     * Passes provided {@link String} if not blank to the provided string {@link Consumer}
     *
     * @param value         the value to check
     * @param valueConsumer non-blank value consumer
     * @since 0.0.9
     */
    public static void ifNotBlank(String value, Consumer<String> valueConsumer) {
        if (isNotBlank(value)) {
            valueConsumer.accept(value);
        }
    }

    /**
     * Returns capitalized variant of provided {@link String} with space as default word delimiter
     *
     * @param source the value to capitalize
     * @return capitalized string or empty string if null or blank string is provided
     * @since 0.0.9
     */
    public static String capitalize(String source) {
        return capitalize(source, ' ');
    }

    /**
     * Returns capitalized variant of provided {@link String} with provided words delimiters
     *
     * @param source     the value to capitalize
     * @param delimiters the words delimiters
     * @return capitalized string or empty string if null or blank string is provided or source string if no delimiters provided
     * @since 0.0.9
     */
    public static String capitalize(String source, char... delimiters) {
        if (isBlank(source)) {
            return EMPTY;
        }
        if (isNull(delimiters) || 0 == delimiters.length) {
            return source;
        }

        Set<Integer> delimiterValues = IntStream.range(0, delimiters.length)
                .mapToObj(idx -> Character.codePointAt(delimiters, idx))
                .collect(Collectors.toSet());

        String lowerCaseSource = source.trim().toLowerCase();
        int sourceLength = lowerCaseSource.length();
        int[] resultValues = new int[sourceLength];
        int resultEndIdx = 0;
        boolean capitalizeNext = false;
        for (int idx = 0; idx < sourceLength; ) {
            int charAt = lowerCaseSource.codePointAt(idx);
            if (delimiterValues.contains(charAt)) {
                capitalizeNext = true;
                idx += Character.charCount(charAt);
                resultValues[resultEndIdx++] = charAt;
                continue;
            }
            if (capitalizeNext || 0 == idx) {
                int newValue = Character.toTitleCase(charAt);
                idx += Character.charCount(newValue);
                resultValues[resultEndIdx++] = newValue;
                capitalizeNext = false;
                continue;
            }
            idx += Character.charCount(charAt);
            resultValues[resultEndIdx++] = charAt;
        }
        return new String(resultValues, 0, resultEndIdx);
    }

    /**
     * Joins provided {@link Collection} of {@link CharSequence} with provided delimiter
     *
     * @param elements  collection of charSequences to join
     * @param delimiter the delimiter
     * @return joint {@link String} or empty string if provided collection is empty or null
     * @since 0.0.9
     */
    public static String safeJoin(Collection<? extends CharSequence> elements, CharSequence delimiter) {
        return CollectionUtils.safeStreamOf(elements)
                .collect(Collectors.joining(delimiter));
    }

    /**
     * Passes provided {@link String} pair if both of them not empty to the provided string {@link BiConsumer}
     *
     * @param left           the first value to check
     * @param right          the second value to check
     * @param valuesConsumer non-empty values consumer
     * @since 0.0.9
     */
    public static void ifBothNotEmpty(String left, String right, BiConsumer<String, String> valuesConsumer) {
        if (isNotEmpty(left) && isNotEmpty(right)) {
            valuesConsumer.accept(left, right);
        }
    }

    /**
     * Passes provided {@link String} pair if both of them not blank to the provided string {@link BiConsumer}
     *
     * @param left           the first value to check
     * @param right          the second value to check
     * @param valuesConsumer non-blank values consumer
     * @since 0.0.9
     */
    public static void ifBothNotBlank(String left, String right, BiConsumer<String, String> valuesConsumer) {
        if (isNotBlank(left) && isNotBlank(right)) {
            valuesConsumer.accept(left, right);
        }
    }

    /**
     * Returns provided value {@link String} or default {@link String} if value is null or empty
     *
     * @param value        that can be null or empty
     * @param defaultValue fallback value
     * @return value if it is not null or empty, otherwise defaultValue
     * @since 0.1.0
     */
    public static String notEmptyOrElse(String value, String defaultValue) {
        return isEmpty(value) ? defaultValue : value;
    }

    /**
     * Conducts named format for provided {@link String} template
     * <p> An example of syntax:
     * <p> "Some example text ${keyOne?`onMissing`} more text ${keyTwo%x} even more text ${keyThree} and a bit more ${keyThree?`onMissing`%x}"
     * <p> Replacement tokens syntax breakdown: ${keyOne?`onMissing`%s}
     * <ul>
     *     <li>Each token should be wrapped with ${}</li>
     *     <li>"keyOne" is an example of key in provided values map</li>
     *     <li>"?`onMissing`" is an example of default value ("onMissing") if token key is missing in values map or associated with null value. Supports alpha-numerics, spaces, punctuation. Optional part of token, can be omitted, if not provided empty string will be used instead</li>
     *     <li>If value associated with key not found and default value is not provided token will returned as is</li>
     *     <li>"%s" is an example of value formatting ("%s"), standard {@link String#format(String, Object...)} format is supported. Optional part of token, can be omitted, if not provided {@link String#valueOf(Object)} will be used instead</li>
     *  </ul>
     *
     * @param template template of resulting string
     * @param values   {@link Map} of values that should replace tokens in provided template
     * @return formatted string with provided values map,
     * or empty string if provided template is empty or null,
     * or template if it is blank or values map is empty or null
     * @since 0.1.0
     */
    public static String namedFormat(String template, Map<String, Object> values) {
        if (isEmpty(template)) {
            return EMPTY;
        }
        if (isBlank(template) || CollectionUtils.isEmpty(values)) {
            return template;
        }
        Transformer<String> transformer = Transformer.of(template);
        NAMED_FORMAT_PATTERN.matcher(template).results().forEach(matchResult -> {
            String format = matchResult.group(3);
            Object providedValue = values.get(matchResult.group(1));
            String group = matchResult.group(0);
            String replacement = isNull(providedValue) ?
                    valueOrDefault(matchResult.group(2), group) : isEmpty(format) ?
                    String.valueOf(providedValue) : String.format(format, providedValue);
            transformer.run(v -> v.replace(group, replacement));
        });
        return transformer.result();
    }

    /**
     * Transfers named groups from provided source {@link String} using provided {@link Pattern} to provided string template
     *
     * @param sourcePattern pattern with named groups
     * @param source        source string which parts should be matched and transferred to resulting string
     * @param template      named template with tokens representing group names, see {@link StringUtils#namedFormat format description can be found here}
     * @return formatted string with transferred values or empty string if provided pattern is null or the template or source is blank
     * @see StringUtils#namedFormat
     * @since 0.1.0
     */
    public static String transferValues(Pattern sourcePattern, String source, String template) {
        if (Objects.isNull(sourcePattern) || isBlank(source) || isBlank(template)) {
            return EMPTY;
        }
        Set<String> keys = NAMED_FORMAT_PATTERN.matcher(template).results()
                .map(mr -> mr.group(1))
                .collect(Collectors.toSet());
        Matcher matcher = sourcePattern.matcher(source);
        Map<String, Object> values = new HashMap<>();
        while (matcher.find()) {
            for (String key : keys) {
                try {
                    values.computeIfAbsent(key, matcher::group);
                } catch (IllegalArgumentException | IllegalStateException ignored) {
                }
            }
        }
        return namedFormat(template, values);
    }
}
